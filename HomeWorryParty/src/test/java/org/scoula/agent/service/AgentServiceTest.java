package org.scoula.agent.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
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
class AgentServiceTest {

    @Autowired
    AgentService agentService;

    @Test
    void fetchAndSaveOffice() {
        agentService.fetchAndSaveOffice();
    }
}