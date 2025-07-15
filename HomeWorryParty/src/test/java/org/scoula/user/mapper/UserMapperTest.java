package org.scoula.user.mapper;

import org.junit.*;
import org.junit.runner.RunWith;
import org.scoula.config.RootConfig;
import org.scoula.security.account.domain.UserVO;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback(true)
    public void testInsertUserAndSelectUser() {
        UserVO user = UserVO.builder()
                .username("박상일")
                .email("ss@test.com")
                .password("pw1234!")
                .phone("010-1234-5678")
                .userType("일반")
                .build();

        userMapper.insertUser(user);

        Assert.assertNotNull(user.getUsername());

        UserVO selected = userMapper.findByUsername(user.getUsername());
        Assert.assertNotNull(selected);
        Assert.assertEquals("ss@test.com", selected.getEmail());
    }

    @Test
    @Rollback(true)
    public void testSelectUserByEmail() {
        UserVO user = UserVO.builder()
                .username("김철수")
                .email("cheolsu@test.com")
                .password("pw5678!")
                .userType("중개사")
                .build();

        userMapper.insertUser(user);

        UserVO found = userMapper.findByUsername("김철수");
        Assert.assertNotNull(found);
        Assert.assertEquals("김철수", found.getUsername());
    }

    @Test
    @Rollback(true)
    public void testUpdateUser() {
        UserVO user = UserVO.builder()
                .username("이수정")
                .email("lee@test.com")
                .password("pw9999!")
                .userType("일반")
                .build();

        userMapper.insertUser(user);

        user.setPhone("010-9999-9999");

        userMapper.update(user);

        UserVO updated = userMapper.findByUsername(user.getUsername());
        Assert.assertEquals("010-9999-9999", updated.getPhone());
    }

//    @Test
//    @Rollback(true)
//    public void testDeleteUser() {
//        UserVO user = UserVO.builder()
//                .username("최삭제")
//                .email("del@test.com")
//                .password("pw0000!")
//                .userType("일반")
//                .regDate(new Date())
//                .updateDate(new Date())
//                .build();
//
//        userMapper.insert(user);
//
//        userMapper.deleteUser(user.getUserId());
//
//        UserVO deleted = userMapper.selectUserById(user.getUserId());
//        Assert.assertNull(deleted);
//    }
}
