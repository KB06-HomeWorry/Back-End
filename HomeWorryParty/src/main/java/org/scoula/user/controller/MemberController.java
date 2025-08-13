package org.scoula.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.util.JwtProcessor;
import org.scoula.user.dto.*;
import org.scoula.user.service.PasswordResetService;
import org.scoula.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
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

    @GetMapping("/getprofile") // 마이페이지 유저 정보 조회
    public ResponseEntity<UserDTO> getProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return ResponseEntity.ok().body(service.getUserById(jwtProcessor.getUserId(token)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/withdraw") // 회원 탈퇴
    public ResponseEntity<?> withdraw(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return ResponseEntity.ok().body(service.withdraw(jwtProcessor.getUserId(token)));
        } else {
            return ResponseEntity.notFound().build();
        }
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
        return ResponseEntity.ok(passwordResetService.passwordVerifyCheck(pdto.getPassword(), jwtProcessor.getUserId(pdto.getToken())));
    }

    @GetMapping("/verify-password") // 비밀번호 재설정 토큰 발급
    public ResponseEntity<?> passwordVerify(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return ResponseEntity.ok().body(passwordResetService.passwordVerify(jwtProcessor.getUserId(token)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
