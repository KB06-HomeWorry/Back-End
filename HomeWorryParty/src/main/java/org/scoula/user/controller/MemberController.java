package org.scoula.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.common.util.UploadFiles;
import org.scoula.security.util.JwtProcessor;
import org.scoula.user.dto.*;
import org.scoula.user.service.PasswordResetService;
import org.scoula.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

//backend 보통 json/text로 vue로 리턴함.
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

    @PostMapping("") //가입해줘
    public ResponseEntity<UserDTO> join(@RequestBody UserJoinDTO member) {
        return ResponseEntity.ok(service.join(member));
    }

    @GetMapping("/getprofile/{token}") // 마이페이지 유저 정보 전달
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

    @GetMapping("/{username}/avatar")
    public void getAvatar(@PathVariable String username,
                          HttpServletResponse response) {
        String avatarPath = "c:/upload/avatar/" + username + ".png";
        File file = new File(avatarPath);
        if (!file.exists()) {  // 아바타 등록이 없는 경우, 디폴트 아바타 이미지 사용
            file = new File("C:/upload/avatar/unknown.png");
        }

        UploadFiles.downloadImage(response, file);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> changeProfile(UserUpdateDTO member) {
        return ResponseEntity.ok(service.update(member));
    }

    @PutMapping("/{username}/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        service.changePassword(changePasswordDTO);
        return ResponseEntity.ok().build();
    }


}
