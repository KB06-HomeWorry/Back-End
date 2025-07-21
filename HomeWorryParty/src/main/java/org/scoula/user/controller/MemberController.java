package org.scoula.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.common.util.UploadFiles;
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

    @GetMapping("/checkusername/{username}") //아이디 중복 체크
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(service.checkDuplicate(username));
    }

    @PostMapping("") //가입해줘
    public ResponseEntity<UserDTO> join(UserJoinDTO member) {
        return ResponseEntity.ok(service.join(member));
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
