package org.scoula.dangerResult.service;

import org.junit.jupiter.api.Test;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
class DangerResultServiceImplTest {
    @Autowired
    DangerResultService dangerResultServiceImpl;

    @Test
    void analysisDangerResult() {
        dangerResultServiceImpl.analysisDangerResult(3L,1L);
    }

    @Test
    void getMessageList() {
        dangerResultServiceImpl.getMessageList(50, 3L);
    }
}