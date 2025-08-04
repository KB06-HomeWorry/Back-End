package org.scoula.documentAnalysis.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.documentAnalysis.dto.IllegalBuildingCheckDTO;
import org.scoula.documentAnalysis.service.DocumentAnalysisServiceImpl;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@WebAppConfiguration // ← 이 줄을 꼭 추가하세요!
@ExtendWith(SpringExtension.class) // 꼭 추가!
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
class DocumentAnalysisControllerTest {

    @Autowired
    DocumentAnalysisServiceImpl documentAnalysisService;

    @Test
    void testGetAllData() throws IOException {
        final int MAX_PER_PAGE = 1000; // API에서 허용하는 최대 요청 수(보통 1000)
        int start = 1;
        int end = start + MAX_PER_PAGE - 1;
        int totalCount = 0;
        boolean firstCall = true;

        while (true) {
            // URL 세팅
            String urlBuilder = "http://openapi.seoul.go.kr:8088" + "/" + URLEncoder.encode("454247417168796e33354265726f77", StandardCharsets.UTF_8) + // 인증키
                    "/" + URLEncoder.encode("json", StandardCharsets.UTF_8) + // 타입
                    "/" + URLEncoder.encode("vBigDjrTitle", StandardCharsets.UTF_8) + // 서비스명
                    "/" + URLEncoder.encode(String.valueOf(start), StandardCharsets.UTF_8) + // 시작
                    "/" + URLEncoder.encode(String.valueOf(end), StandardCharsets.UTF_8);   // 종료

            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");
            //System.out.println("Requesting: " + url);
            //System.out.println("Response code: " + conn.getResponseCode());

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();

            // 1회차에 전체 데이터 개수 파악 (firstCall 플래그 사용)
            if (firstCall) {
                totalCount = extractTotalCount(json); // 아래에 extractTotalCount 함수 참고
                //System.out.println("전체 데이터 개수: " + totalCount);
                firstCall = false;
            }

            // 데이터 파싱 및 저장(원래대로)
            parse(json);

            rd.close();
            conn.disconnect();

            // 다음 루프 세팅
            start += MAX_PER_PAGE;
            end = Math.min(start + MAX_PER_PAGE - 1, totalCount);

            // 모두 다 가져왔으면 break
            if (start > totalCount) break;
        }
    }

    // 전체 데이터 개수 추출 함수
    private int extractTotalCount(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        Map<String, Object> vBigDjrTitle = (Map<String, Object>) map.get("vBigDjrTitle");
        return (int) vBigDjrTitle.get("list_total_count");
    }

    public void parse(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 1. 전체 JSON 파싱 (Map 사용)
        Map<String, Object> jsonMap = mapper.readValue(json, Map.class);

        // 2. vBigDjrTitle 추출
        Map<String, Object> vBigDjrTitle = (Map<String, Object>) jsonMap.get("vBigDjrTitle");

        // 3. row 배열 추출
        List<Map<String, Object>> rowList = (List<Map<String, Object>>) vBigDjrTitle.get("row");

        // 4. 각 row를 DTO로 변환 (Jackson으로 개별 매핑)
        for (Map<String, Object> rowItem : rowList) {
            // 1) Map → JSON string
            String rowJson = mapper.writeValueAsString(rowItem);
            // 2) JSON string → DTO
            IllegalBuildingCheckDTO dto = mapper.readValue(rowJson, IllegalBuildingCheckDTO.class);
            // 이제 dto에 데이터가 들어있음!
            // 여기에 DB 저장, 리스트 저장, 가공 등 원하는 처리

            if(checkIllegal(dto)){
                documentAnalysisService.insertIllegalBuildingData(dto);
                //System.out.println(dto);
            }

            //System.out.println(dto);
        }
    }

    /**
     * 불법 건축물 판정: 불법이면 true, 아니면 false
     * (실무에서는 보통 판정 사유까지 반환해주는 것이 좋음)
     */
    public boolean checkIllegal(IllegalBuildingCheckDTO dto) {
        StringBuilder sb = new StringBuilder();
        String judgeReason = "";

        // 1. 대장구분/종류에 '위반', '불법', '임시'가 포함되면 불법
        String[] keywords = {"위반", "불법", "임시"};
        if (dto.getLdgrSeCdNm() != null) {
            for (String keyword : keywords) {
                if (dto.getLdgrSeCdNm().contains(keyword)) {
                    sb.append("대장구분명에 '").append(keyword).append("' 포함").append("<br></br>");
                    dto.setJudgeResult("불법 의심");
                }
            }
        }
        if (dto.getLdgrKindCdNm() != null) {
            for (String keyword : keywords) {
                if (dto.getLdgrKindCdNm().contains(keyword)) {
                    sb.append("대장구분명에 '").append(keyword).append("' 포함").append("<br></br>");
                    dto.setJudgeResult("불법 의심");
                }
            }
        }

        // 2. 건폐율 기준 초과 (100% 넘으면 불법 의심, 실제 기준은 용도지역별 별도 처리 가능)
        if (dto.getBdcvrt() != null && dto.getBdcvrt() > 100.0) {
            dto.setJudgeReason("건폐율 100% 초과");
            sb.append("건폐율 100% 초과").append("<br></br>");
            dto.setJudgeResult("불법 의심");
        }

        // 3. 연면적이 비정상적으로 큰 경우(예: 99,999㎡ 초과)
        if (dto.getGfa() != null && dto.getGfa() > 99999) {
            sb.append("연면적 99,999㎡ 초과(비정상)").append("<br></br>");
            dto.setJudgeResult("불법 의심");
        }

        // 4. 허가일자/사용승인일자가 모두 없으면 무허가로 간주
        if ((dto.getPrmsnYmd() == null || dto.getPrmsnYmd().isBlank())
                && (dto.getUseAprvYmd() == null || dto.getUseAprvYmd().isBlank())) {
            sb.append("허가일자/사용승인일자 없음").append("<br></br>");
            dto.setJudgeResult("불법 의심");
        }

        // 5. 주용도/기타용도에 불법 변경 흔적 (예: '창고', '사무실', '임시', '가설', '불법', '위반')
        String[] illegalUsages = {"임시", "가설", "창고", "불법", "위반"};
        if (dto.getMnUsgCdNm() != null) {
            for (String kw : illegalUsages) {
                if (dto.getMnUsgCdNm().contains(kw)) {
                    sb.append("주용도에 '").append(kw).append("' 포함").append("<br></br>");
                    dto.setJudgeResult("불법 의심");
                }
            }
        }
        if (dto.getEtcUsgCn() != null) {
            for (String kw : illegalUsages) {
                if (dto.getEtcUsgCn().contains(kw)) {
                    sb.append("기타용도에 '").append(kw).append("' 포함").append("<br></br>");
                    dto.setJudgeResult("불법 의심");
                }
            }
        }

        // 6. 내진설계 미적용 (Y/N, 실제는 법적 의무대상일 때만 적용. 단순 N이면 의심)
        if (dto.getRserDesignAplcnYn() != null && dto.getRserDesignAplcnYn().equalsIgnoreCase("N")) {
            sb.append("내진설계 미적용").append("<br></br>");
            dto.setJudgeResult("불법 의심");
        }

        // 8. 지붕 정보에 불법/임시/비정상 유형 포함 시 불법 의심
        String[] illegalRoofs = {"임시", "가설", "비닐", "천막", "슬레이트", "불법", "위반"};

        if (dto.getRoofCdNm() != null) {
            for (String kw : illegalRoofs) {
                if (dto.getRoofCdNm().contains(kw)) {
                    sb.append("지붕구조에 '").append(kw).append("' 포함").append("<br></br>");
                    dto.setJudgeResult("불법 의심");
                }
            }
        }else{
            sb.append("지붕 구조 데이터 누락").append("<br></br>");
            dto.setJudgeResult("불법 의심");
        }

        if (dto.getEtcRoofNm() != null) {
            for (String kw : illegalRoofs) {
                if (dto.getEtcRoofNm().contains(kw)) {
                    sb.append("기타지붕명에 '").append(kw).append("' 포함").append("<br></br>");
                    dto.setJudgeResult("불법 의심");
                }
            }
        }

        judgeReason = sb.toString();
        if(!judgeReason.isEmpty()){
            dto.setJudgeReason(judgeReason);
            return true;
        }

        // 모든 조건에 해당되지 않으면 정상
        dto.setJudgeReason("정상(불법 아님)");
        dto.setJudgeResult("정상");
        return false;
    }

}