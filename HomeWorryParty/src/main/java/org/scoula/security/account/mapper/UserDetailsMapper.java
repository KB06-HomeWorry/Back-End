package org.scoula.security.account.mapper;

import org.scoula.security.account.domain.UserVO;

public interface UserDetailsMapper {
    UserVO getByUserEmail(String email); // 이메일로 유저 검색

    String getEmailByUsername(String username); // 유저이름으로 이메일 검색
}
