package org.scoula.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.user.domain.PasswordRewriteVO;
import org.scoula.user.dto.PasswordResetTokenDTO;
import org.scoula.user.dto.PasswordRewriteDTO;
import org.scoula.user.dto.UserDTO;
import org.scoula.user.exception.PasswordMissmatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class PasswordResetService {
    final PasswordEncoder passwordEncoder;
    final JavaMailSender mailSender;
    final UserService userService;

    @Transactional
    public PasswordResetTokenDTO PasswordReset(String userEmail) { // 비밀번호 재설정 이메일 발송
        // 1. 해당 이메일을 가진 사용자가 있는지 확인
         if (!userService.existsByEmail(userEmail)) {
             throw new RuntimeException("해당 이메일의 유저가 존재하지 않습니다.: " + userEmail);
         }

        // 2. 임시 재설정 토큰 생성
        String resetToken = generateResetToken();

        // 3. DB에 토큰 저장 (사용자 정보와 만료 시간을 함께 저장해야 함)
        userService.savePasswordResetToken(new PasswordResetTokenDTO(userEmail, resetToken, LocalDateTime.now()));

        // 4. 재설정 링크 생성
        String resetLink = "http://localhost:5173/auth/change-password/" + resetToken;

        // 5. 이메일 발송
        sendPasswordResetEmail(userEmail, resetLink);

        return userService.getemail(userEmail);
    }

    public Boolean passwordVerifyCheck(String password, String username) { // 사용자 이름과 비밀번호가 일치하는지 확인
        UserDTO member = userService.get(username);

        return passwordEncoder.matches(password, member.getPassword());
    }

    public String passwordVerify(String password, String username) { // 비밀번호 재설정 토큰을 발급하고 토큰 정보 반환
        UserDTO member = userService.get(username);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new PasswordMissmatchException();
        }

        String resetToken = generateResetToken();

        userService.savePasswordResetToken(new PasswordResetTokenDTO(member.getEmail(), resetToken, LocalDateTime.now()));

        return resetToken;
    }

    @Transactional // 비밀번호 재설정
    public void PasswordRewrite(PasswordRewriteDTO dto){
        PasswordRewriteVO vo = dto.toVO();

        // 1. 입력받은 토큰이 존재하는지를 확인
        if (!userService.existsByToken(vo.getToken())){
            throw new RuntimeException("토큰이 존재하지 않습니다: " + vo.getToken());
        }

        // 2. 토큰이 만료되었는지 확인
        PasswordResetTokenDTO prt = userService.gettoken(vo.getToken());
        LocalDateTime now = LocalDateTime.now();
        if (!prt.getExpDate().isAfter(now)){
            throw new RuntimeException("토큰이 만료되었습니다: " + prt.getExpDate());
        }

        // 3. user db의 비밀번호 변경
        UserDTO user = userService.getUserByEmail(prt.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.PasswordRewrite(user);

        // 4. 사용된 토큰 정보 db에서 삭제
        userService.deleteToken(prt.getEmail());

    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    } // 고유 토큰 생성

    private void sendPasswordResetEmail(String toEmail, String link) { // 이메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[집걱정단] 비밀번호 재설정 안내");
        message.setText("안녕하세요,\n\n비밀번호 재설정을 위해 아래 링크를 클릭해주세요.\n\n" + link + "\n\n감사합니다.");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            // 이메일 발송 실패 시 로깅 또는 예외 처리
            e.printStackTrace();
            throw new RuntimeException("이메일 발송에 실패했습니다.");
        }
    }
}