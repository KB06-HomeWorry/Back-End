package org.scoula.user.mapper;


import org.scoula.security.account.domain.UserVO;
import org.scoula.user.domain.PasswordResetTokenVO;

public interface UserMapper {
    UserVO get(String username); // 사용자명으로 사용자 정보 검색

    UserVO findByemail(String email); // 사용자 이메일로 사용자 정보 검색

    UserVO findById(Long userId); // 사용자 아이디로 사용자 정보 검색

    PasswordResetTokenVO findByToken(String token); // 비밀번호 재설정 토큰으로 토큰 발급 정보 검색

    PasswordResetTokenVO getemail(String email); // 사용자 이메일로 토큰 발급 정보 검색

    int withdraw(Long userId); // 회원 정보 삭제

    int insertPRT(PasswordResetTokenVO passwordResetTokenVO); // 비밀번호 재설정 토큰 생성

    int updatePRT(PasswordResetTokenVO passwordResetTokenVO); // 비밀번호 재설정 토큰 수정

    int insertUser(UserVO member); // 회원 정보 추가

    int passwordRewrite(UserVO user); // 비밀번호 수정

    int deleteToken(String email); // 비밀번호 재설정 토큰 삭제

}
