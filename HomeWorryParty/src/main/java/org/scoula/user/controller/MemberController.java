package org.scoula.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.util.JwtProcessor;
import org.scoula.user.dto.*;
import org.scoula.user.service.PasswordResetService;
import org.scoula.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    final UserService service;
    final PasswordResetService passwordResetService;
    final JwtProcessor jwtProcessor;

    @GetMapping("/checkusername/{email}") //아이디 중복 체크
    public ResponseEntity<Boolean> checkUsername(@PathVariable String email) {
        return ResponseEntity.ok().body(service.existsByEmail(email));
    }

    @GetMapping("/checkNameAndEmail") // 사용자 이름과 이메일이 일치하는지 체크
    public ResponseEntity<Boolean> checkNameAndEmail(@RequestParam String name, @RequestParam String email) {
        return ResponseEntity.ok().body(service.checkNameAndEmail(name, email));
    }

    @PostMapping("") //회원가입
    public ResponseEntity<UserDTO> join(@RequestBody UserJoinDTO member) {
        return ResponseEntity.ok(service.join(member));
    }

    @GetMapping("/getprofile/{token}") // 마이페이지 유저 정보 조회
    public ResponseEntity<UserDTO> getProfile(@PathVariable String token) {
        return ResponseEntity.ok().body(service.get(jwtProcessor.getUsername(token)));
    }

    @DeleteMapping("/withdraw/{token}") // 회원 탈퇴
    public ResponseEntity<?> withdraw(@PathVariable String token) {
        return ResponseEntity.ok().body(service.withdraw(jwtProcessor.getUsername(token)));
    }

    @GetMapping("/resetpassword/{email}") // 비밀번호 변경 이메일 보내기
    public ResponseEntity<PasswordResetTokenDTO> passwordResetEmail(@PathVariable String email) {
        return ResponseEntity.ok(passwordResetService.PasswordReset(email));
    }

    @PostMapping("/resetpassword")// 비밀번호 변경 신청
    public ResponseEntity<?> passwordReset(@RequestBody PasswordRewriteDTO prdto){
        passwordResetService.PasswordRewrite(prdto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-password-check") // 비밀번호 일치 확인
    public ResponseEntity<?> passwordVerifyCheck(@RequestBody VerifyPasswordDTO pdto){
        return ResponseEntity.ok(passwordResetService.passwordVerifyCheck(pdto.getPassword(), jwtProcessor.getUsername(pdto.getToken())));
    }

    @GetMapping("/verify-password/{token}") // 비밀번호 재설정 토큰 발급
    public ResponseEntity<?> passwordVerify(@PathVariable String token) {
        return ResponseEntity.ok().body(passwordResetService.passwordVerify(jwtProcessor.getUsername(token)));
    }

}
