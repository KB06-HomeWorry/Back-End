package org.scoula.user.mapper;


import org.scoula.security.account.domain.UserVO;
import org.scoula.user.dto.ChangePasswordDTO;
import org.scoula.security.account.domain.AuthVO;

public interface UserMapper {
    //회원검색
    //id중복체크
    //회원가입
    //권한(role)을 추가
    UserVO get(String username);

    UserVO findByUsername(String username); // id 중복 체크시 사용

    int insertUser(UserVO member); // 회원 정보 추가

    int insertAuth(AuthVO auth); // 회원 권한 정보 추가

    int update(UserVO member);

    int updatePassword(ChangePasswordDTO changePasswordDTO);

}
