package org.scoula.security.account.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.account.domain.AuthVO;
import org.scoula.security.account.domain.UserVO;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Log4j2
class UserDetailsMapperTest {

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Test
    void get() {
        UserVO memberVO = userDetailsMapper.get("홍길동");
        log.info(memberVO);
        //List<AuthVO) 궁금..??
        List<AuthVO> authList = memberVO.getAuthList();
        log.info(authList.size()); //3개
        System.out.println("==============================");
        for (AuthVO authVO : authList) {
            log.info(authVO);
        }
    }
}