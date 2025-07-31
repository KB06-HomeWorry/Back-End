package org.scoula.ai.service;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
class AIServiceImplTest {

    @Autowired
    private AIServiceImpl AIServiceImpl;

    @Test
    void simplifySentence() {
        // given
        String input = "유상 임대차 계약의 조건에 따라 임차인은 매월 정해진 금액을 임대인에게 지급해야 합니다.";

        // when
        String result = null;
        try {
            result = AIServiceImpl.simplifySentence(input);
        } catch (Exception e) {
            log.error("AI API 호출 중 오류 발생", e);
        }

        // then
        assertNotNull(result); // 실제 응답이 null이 아님을 확인
        log.info("AI 변환 결과: {}", result);
    }
}