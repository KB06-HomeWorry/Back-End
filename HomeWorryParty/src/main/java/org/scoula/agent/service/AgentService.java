package org.scoula.agent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.domain.AgentReviewVO;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.agent.dto.AgentReviewDTO;
import org.scoula.agent.dto.TrustScoreDTO;
import org.scoula.agent.mapper.AgentMapper;
import org.scoula.agent.model.Office;
import org.scoula.agent.model.OpenApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final String apiKey = "6e427a636e6361723130354547546359";
    private final String openApiURL = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/landBizInfo/";
    private static final int PAGE_SIZE = 1000;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(0\\d{1,3}[-\\s]?\\d{3,4}[-\\s]?\\d{4})|(\\d{3,4}[-\\s]?\\d{4})");

    // OpenAPI 마지막 업데이트 날짜 반환
    public LocalDateTime getLastUpdateTime() {
        LocalDateTime updateTime = mapper.findUpdatedAt();

        if (updateTime == null) {
            return LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        } else {
            return updateTime;
        }
    }

    @Transactional // OpenAPI 데이터 저장
    public String fetchAndSaveOffice() {

        if (ChronoUnit.HOURS.between(getLastUpdateTime(), LocalDateTime.now()) <= 24) {
            return "마지막 업데이트로부터 하루가 지나지 않아 업데이트를 종료합니다.";
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

                if (apiResponse == null || apiResponse.getLandBizInfo() == null) {
                    System.out.println("API 응답이 비어있거나 landBizInfo가 없습니다. 페이지네이션을 종료합니다.");
                    break;
                }

                OpenApiResponse.LandBizInfo landBizInfo = apiResponse.getLandBizInfo();

                if (firstFetch) {
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
                if (offices.size() < PAGE_SIZE) {
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

    // OpenAPI 데이터 필터링
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

    // 전화번호 정규화
    private String cleanAndStandardizePhone(String originalPhone) {
        if (originalPhone == null || originalPhone.trim().isEmpty()) {
            return null;
        }

        String cleaned = originalPhone;

        // 1. 괄호 안의 내용 제거 (예: "02-444-5070(21.2.2.)" -> "02-444-5070")
        cleaned = cleaned.replaceAll("\\([^)]*\\)", "").trim();

        // 2. 불필요한 쉼표, 슬래시, 한글 괄호 등 제거 및 연속된 공백 하나로 줄이기
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

        // 5. 지역번호가 생략된 경우 '02-' 추가
        if (digitsOnly.length() == 7 || digitsOnly.length() == 8) {
            if (!digitsOnly.startsWith("0")) { // 0으로 시작하지 않는다면 지역번호 생략으로 간주
                // 국번과 뒷번호 사이에 하이픈이 없는 경우 추가
                if (!extractedNumber.contains("-")) {
                    if (digitsOnly.length() == 7) { // 3자리 국번 + 4자리 번호
                        extractedNumber = digitsOnly.substring(0, 3) + "-" + digitsOnly.substring(3, 7);
                    } else { // 4자리 국번 + 4자리 번호
                        extractedNumber = digitsOnly.substring(0, 4) + "-" + digitsOnly.substring(4, 8);
                    }
                }
                return "02-" + extractedNumber;
            }
        }

        // 6. 최종적으로 하이픈을 사용하여 표준화
        return extractedNumber;
    }

    // 중개사 상세 정보 조회
    public AgentDetailDTO getAgentDetail(Long officeId) {
        return AgentDetailDTO.of(mapper.getAgentDetail(officeId));
    }

    // 중개사 리뷰 전체 조회
    public List<AgentReviewDTO> getAgentReviews(Long officeId) {
        List<AgentReviewVO> vo = mapper.getAgentReviews(officeId);
        List<AgentReviewDTO> list = new ArrayList<>();

        if (vo == null || vo.isEmpty()) {
            return list;
        }

        for (AgentReviewVO arvo : vo) {
            list.add(AgentReviewDTO.of(arvo));
        }

        return list;
    }

    // 시간 가중치 계산
    public int calcTimeWeight(LocalDateTime created) {
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), created);
        if (days <= 30) {
            return 10;
        } else if (days <= 90) {
            return 7;
        } else if (days <= 180) {
            return 4;
        } else {
            return 1;
        }
    }

    // 중개사 리뷰 저장
    public void writeAgentReview(AgentReviewDTO agentReviewDTO) {
        mapper.writeAgentReview(agentReviewDTO.toVO());
    }

    // 중개사 신뢰지수 계산 및 반환
    public TrustScoreDTO getAgentScore(Long officeId) {
        int totalWeight = 0;
        double totalAccuracy = 0;
        double totalTransparency = 0;
        double totalProfessionalism = 0;
        double totalAccountability = 0;

        for (AgentReviewVO vo : mapper.getAgentReviews(officeId)) {
            int timeWeight = calcTimeWeight(vo.getCreatedAt());

            totalWeight += timeWeight;
            totalAccuracy += timeWeight * vo.getListingAccuracyScore();
            totalTransparency += timeWeight * vo.getCostTransparencyScore();
            totalProfessionalism += timeWeight * vo.getProfessionalismScore();
            totalAccountability += timeWeight * vo.getAccountabilityScore();
        }

        double totalTrustScore = (totalAccuracy * 0.4 +
                totalTransparency * 0.3 +
                totalAccountability * 0.2 +
                totalProfessionalism * 0.1) / totalWeight;

        totalTrustScore = Math.round((totalTrustScore + 6.2) * 1000 / 9.6) / 10.0;
        totalAccuracy = Math.round((totalAccuracy / totalWeight + 5) * 1000 / 8) / 10.0;
        totalTransparency = Math.round((totalTransparency / totalWeight + 5) * 1000 / 8) / 10.0;
        totalProfessionalism = Math.round((totalProfessionalism / totalWeight + 7) * 1000 / 10) / 10.0;
        totalAccountability = Math.round((totalAccountability / totalWeight + 10) * 1000 / 15) / 10.0;

        return new TrustScoreDTO(totalTrustScore, totalAccuracy, totalTransparency, totalProfessionalism, totalAccountability);
    }
}
