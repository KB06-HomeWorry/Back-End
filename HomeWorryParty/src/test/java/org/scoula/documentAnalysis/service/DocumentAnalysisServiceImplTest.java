package org.scoula.documentAnalysis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.agent.dto.AgentDetailDTO;
import org.scoula.config.RootConfig;
import org.scoula.documentAnalysis.dto.DocumentAnalysisDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisResultDTO;
import org.scoula.documentAnalysis.dto.DocumentSthRiskDTO;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration // ← 이 줄을 꼭 추가하세요!
@ExtendWith(SpringExtension.class) // 꼭 추가!
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Slf4j
class DocumentAnalysisServiceImplTest {
    @Autowired
    private DocumentAnalysisServiceImpl documentAnalysisService;

    @Test
    void checkCertified() {
    }

    @Test
    void checkHouseAddress() {
        DocumentAnalysisResultDTO documentAnalysisResultDTO = new  DocumentAnalysisResultDTO();
        documentAnalysisService.checkHouseAddress("%성북구 동소문동4가 136",
                documentAnalysisResultDTO);
        log.info(String.valueOf(documentAnalysisResultDTO));

        documentAnalysisService.checkHouseAddress("%송파구 석촌동 37",
                documentAnalysisResultDTO);
        log.info(String.valueOf(documentAnalysisResultDTO));

    }

    @Test
    void checkDocumentAgent() {
//        DocumentAnalysisResultDTO documentAnalysisResultDTO = new  DocumentAnalysisResultDTO();
//        documentAnalysisService.checkDocumentAgent(null,
//                "%광진구 화양동 9-32");
    }

    @Test
    void checkDocumentSthRisk() {
        DocumentAnalysisResultDTO documentAnalysisResultDTO = new DocumentAnalysisResultDTO();
        DocumentSthRiskDTO documentSthRiskDTO = new DocumentSthRiskDTO();
        documentSthRiskDTO.setType("전세");
        documentSthRiskDTO.setPrice(1300000L);
        documentSthRiskDTO.setSize(20L);
        documentAnalysisService.checkDocumentSthRisk(documentSthRiskDTO,
                "서울특별시 광진구 자양동 0229-0016 (229-16)",
                documentAnalysisResultDTO);

        documentSthRiskDTO.setType("월세");
        documentSthRiskDTO.setPrice(10000000L);
        documentSthRiskDTO.setMonthlyPrice(300000L);
        documentAnalysisService.checkDocumentSthRisk(documentSthRiskDTO,
                "서울특별시 광진구 자양동 0229-0016 (229-16)",
                documentAnalysisResultDTO);

        documentSthRiskDTO.setType("매매");
        documentSthRiskDTO.setPrice(750000000L);
        documentAnalysisService.checkDocumentSthRisk(documentSthRiskDTO,
                "서울특별시 광진구 자양동 0229-0016 (229-16)",
                documentAnalysisResultDTO);

        log.info(String.valueOf(documentAnalysisResultDTO));
    }
}