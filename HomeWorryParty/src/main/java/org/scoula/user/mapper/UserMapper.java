package org.scoula.user.mapper;


import org.scoula.security.account.domain.UserVO;
import org.scoula.user.domain.PasswordResetTokenVO;
import org.scoula.user.dto.ChangePasswordDTO;
import org.scoula.security.account.domain.AuthVO;

public interface UserMapper {
    //회원검색
    //id중복체크
    //회원가입
    //권한(role)을 추가
    UserVO get(String username);

    UserVO findByUsername(String username); // id 중복 체크시 사용
    
    UserVO findByemail(String email); // 비밀번호 재설정에 사용

    PasswordResetTokenVO findByToken(String token);

    PasswordResetTokenVO getemail(String email);

    int insertPRT(PasswordResetTokenVO passwordResetTokenVO);

    int insertUser(UserVO member); // 회원 정보 추가

    int insertAuth(AuthVO auth); // 회원 권한 정보 추가

    int update(UserVO member);

    int passwordRewrite(UserVO user);

    int deleteToken(String email);

    int updatePassword(ChangePasswordDTO changePasswordDTO);

}
