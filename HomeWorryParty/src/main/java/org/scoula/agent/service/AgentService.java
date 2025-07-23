package org.scoula.agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.mapper.AgentMapper;
import org.scoula.agent.model.Office;
import org.scoula.agent.model.OpenApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey = "6e427a636e6361723130354547546359";
    private final String openApiURL = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/landBizInfo/";
    private static final int PAGE_SIZE = 1000;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(0\\d{1,3}[-\\s]?\\d{3,4}[-\\s]?\\d{4})|(\\d{3,4}[-\\s]?\\d{4})");

    public LocalDateTime getLastUpdateTime(){
        LocalDateTime updateTime = mapper.findUpdatedAt();

        if (updateTime == null){
            return LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        } else {
            return updateTime;
        }
    }

    @Transactional
    public String fetchAndSaveOffice(){

        if (ChronoUnit.HOURS.between(getLastUpdateTime(), LocalDateTime.now()) <= 24){
            return "마지막 업데이트로부터 하루가 지나지 않아 업데이트를 중지합니다.";
        }

        int startIndex = 1;
        int totalDataCount = 0;
        boolean firstFetch = true;
        int processedCount = 0;

        do {
            int endIndex = startIndex + PAGE_SIZE - 1;
            String url = openApiURL + startIndex + '/' + endIndex + '/';

            try {
                OpenApiResponse apiResponse = restTemplate.getForObject(url, OpenApiResponse.class);

                if (apiResponse ==  null || apiResponse.getLandBizInfo() == null) {
                    System.out.println("API 응답이 비어있거나 landBizInfo가 없습니다. 페이지네이션을 종료합니다.");
                    break;
                }

                OpenApiResponse.LandBizInfo landBizInfo = apiResponse.getLandBizInfo();

                if (firstFetch){
                    totalDataCount = landBizInfo.getListTotalCount();
                    System.out.println("총 예상 데이터 건수: " + totalDataCount);
                    firstFetch = false;
                }

                List<Office> offices = landBizInfo.getRow();

                if (offices == null || offices.isEmpty()) {
                    System.out.println("더 이상 가져올 데이터가 없거나 row가 비어있습니다. 페이지네이션을 종료합니다.");
                    break;
                }

                List<Office> cleanedOffices = offices.stream()
                        .peek(office -> {
                            String originalPhone = office.getPhone();
                            if (originalPhone != null) {
                                String cleanedPhone = cleanAndStandardizePhone(originalPhone);
                                office.setPhone(cleanedPhone);
                            }
                        }).toList();

                List<Office> filteredOffices = filterOffices(cleanedOffices);

                if (!filteredOffices.isEmpty()) {
                    mapper.saveAll(filteredOffices);
                    processedCount += filteredOffices.size();
                    System.out.printf("인덱스 %d~%d 범위의 원본 데이터 %d개 중 %d개 저장 완료 (필터링 후).%n",
                            startIndex, endIndex, offices.size(), filteredOffices.size());
                } else {
                    System.out.printf("인덱스 %d~%d 범위에서 필터링 조건에 맞는 데이터가 없습니다.%n", startIndex, endIndex);
                }

                startIndex += PAGE_SIZE;
                if (offices.size() < PAGE_SIZE){
                    System.out.println("현재 페이지의 데이터 수가 PAGE_SIZE보다 작습니다. 마지막 페이지로 간주하여 종료합니다.");
                    break;
                }
                if (startIndex > totalDataCount && totalDataCount != 0) {
                    System.out.println("다음 시작 인덱스가 총 데이터 건수를 초과했습니다. 페이지네이션을 종료합니다.");
                    break;
                }

            } catch (Exception e) {
                System.err.println("OpenAPI 데이터 호출 또는 파싱 중 오류 발생 (startIndex: " + startIndex + "): " + e.getMessage());
                e.printStackTrace();
                break;
            }
        } while (startIndex <= totalDataCount);

        mapper.saveUpdateAt();

        return "OpenAPI 데이터 전체 처리 완료. 총 저장된 필터링 데이터: " + processedCount + "개";
    }

    private List<Office> filterOffices(List<Office> offices) {
        return offices.stream()
                .filter(office -> "광진구".equals(office.getGu()))
                .filter(office -> {
                    String phone = office.getPhone();
                    // 1. 전화번호가 null이 아니고, 비어있지 않고, 하이픈(-)으로만 이루어져 있지 않아야 함
                    if (phone == null || phone.trim().isEmpty() || phone.matches("^-+$")) {
                        return false;
                    }
                    // 2. 전화번호 맨 뒷자리가 4자리인지 확인
                    String[] phoneArray = phone.split("-");
                    if (phoneArray.length != 3) {
                        return false;
                    } else return phoneArray[2].length() == 4;
                })
                .collect(Collectors.toList());
    }

    private String cleanAndStandardizePhone(String originalPhone){
        if (originalPhone == null || originalPhone.trim().isEmpty()) {
            return null;
        }

        String cleaned = originalPhone;

        // 1. 괄호 안의 내용 제거 (예: "02-444-5070(21.2.2.)" -> "02-444-5070")
        cleaned = cleaned.replaceAll("\\([^)]*\\)", "").trim();

        // 2. 불필요한 쉼표, 슬래시, 한글 괄호 등 제거 및 연속된 공백 하나로 줄이기
        // "02-467-8289 02-462-0321" -> "02-467-8289 02-462-0321" (공백 유지)
        // "458-8173, 456-4555" -> "458-8173 456-4555"
        cleaned = cleaned.replaceAll("[,/]", " ").replaceAll("\\s+", " ").trim();

        // 3. 정규 표현식으로 첫 번째 유효한 전화번호 패턴만 추출
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(cleaned);
        String extractedNumber = null;
        if (matcher.find()) {
            extractedNumber = matcher.group(0); // 매칭된 전체 문자열 (그룹 0)
        } else {
            // 정규식으로 찾지 못했다면, 숫자와 하이픈만 남기고 최대한 정제해봄 (최후의 수단)
            extractedNumber = cleaned.replaceAll("[^0-9-]", "").trim();
        }

        if (extractedNumber == null || extractedNumber.isEmpty()) {
            return null;
        }

        // 4. 하이픈이 없는 경우에 대비하여 숫자만 있는 문자열 추출
        String digitsOnly = extractedNumber.replaceAll("[^0-9]", "");

        // 5. 지역번호가 생략된 경우 '02-' 추가 (서울 기준)
        // - 총 길이가 7자리 또는 8자리 (숫자만)이고,
        // - 일반적인 지역번호(2~3자리)+국번(3~4자리)+뒷번호(4자리) 형태가 아닌 경우 (예: "457-3004")
        // - 이미 0으로 시작하는 지역번호가 붙어있지 않은 경우
        if (digitsOnly.length() == 7 || digitsOnly.length() == 8) {
            if (!digitsOnly.startsWith("0")) { // 0으로 시작하지 않는다면 지역번호 생략으로 간주
                // 국번과 뒷번호 사이에 하이픈이 없는 경우 추가
                if (!extractedNumber.contains("-")) {
                    if (digitsOnly.length() == 7) { // 3자리 국번 + 4자리 번호
                        extractedNumber = digitsOnly.substring(0,3) + "-" + digitsOnly.substring(3,7);
                    } else { // 4자리 국번 + 4자리 번호 (ex. 1588-XXXX 같은 콜센터 번호 등)
                        // 이 경우 02를 붙이는 것은 적절하지 않을 수 있으나, 요구사항에 맞춰 처리
                        // 혹은 02-를 붙이는 대신 그대로 유지하는 것이 더 자연스러울 수 있음.
                        // 여기서는 일단 "02-"를 붙이는 것으로 가정합니다.
                        extractedNumber = digitsOnly.substring(0,4) + "-" + digitsOnly.substring(4,8);
                    }
                }
                return "02-" + extractedNumber;
            }
        }

        // 6. 최종적으로 하이픈을 사용하여 표준화 (연속된 하이픈 제거 및 숫자만 추출 후 다시 하이픈 포맷팅 가능)
        // 현재 PHONE_NUMBER_EXTRACT_PATTERN이 이미 하이픈을 포함하므로, 추가적인 포맷팅은 필요 없을 수도 있음
        // 다만, 예를 들어 "02 1234 5678" -> "02-1234-5678"로 만들고 싶다면 추가 로직 필요
        // 여기서는 숫자와 하이픈만 남기는 것으로 충분하다고 판단합니다.
        return extractedNumber;
    }
}
