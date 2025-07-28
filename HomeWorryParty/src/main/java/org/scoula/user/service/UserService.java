package org.scoula.user.service;

import org.scoula.user.dto.*;

public interface UserService {

    boolean checkNameAndEmail(String username, String email); // 사용자 이름과 이메일이 일치하는지 체크

    boolean existsByEmail(String email); // 이메일로 사용자가 이미 존재하는지 체크

    boolean existsByToken(String token); // 비밀번호 재설정 토큰이 존재하는지 체크

    void savePasswordResetToken(PasswordResetTokenDTO passwordResetToken); // 비밀번호 재설정 토큰 저장

    UserDTO get(String username); // 사용자 이름으로 사용자 정보 검색

    PasswordResetTokenDTO getemail(String email); // 이메일로 비밀번호 재설정 토큰 발급 정보 검색

    PasswordResetTokenDTO gettoken(String token); // 비밀번호 재설정 토큰으로 비밀번호 재설정 토큰 발급 정보 검색

    UserDTO getUserByEmail(String email); // 이메일로 사용자 정보 검색

    UserDTO join(UserJoinDTO member); // 사용자 정보 저장

    boolean withdraw(String username); // 사용자 정보 삭제

    void PasswordRewrite(UserDTO member); // 사용자 비밀번호 수정

    void deleteToken(String email); // 비밀번호 재설정 토큰 삭제

}
