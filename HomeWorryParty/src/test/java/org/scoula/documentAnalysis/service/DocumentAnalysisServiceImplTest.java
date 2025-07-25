package org.scoula.documentAnalysis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.documentAnalysis.dto.DocumentAnalysisResultDTO;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

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
        documentAnalysisService.checkHouseAddress("서울특별시 성북구 동소문동4가 136",
                documentAnalysisResultDTO);
        log.info(String.valueOf(documentAnalysisResultDTO));

        documentAnalysisService.checkHouseAddress("서울특별시 송파구 석촌동 37",
                documentAnalysisResultDTO);
        log.info(String.valueOf(documentAnalysisResultDTO));

    }

    @Test
    void checkDocumentAgent() {
    }

    @Test
    void checkDocumentSthRisk() {
    }
}