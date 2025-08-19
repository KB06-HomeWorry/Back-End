package org.scoula.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.user.domain.PasswordRewriteVO;
import org.scoula.user.dto.PasswordResetTokenDTO;
import org.scoula.user.dto.PasswordRewriteDTO;
import org.scoula.user.dto.UserDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
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
        // 해당 이메일을 가진 사용자가 있는지 확인
         if (!userService.existsByEmail(userEmail)) {
             throw new RuntimeException("해당 이메일의 유저가 존재하지 않습니다.: " + userEmail);
         }

        // 임시 재설정 토큰 생성
        String resetToken = generateResetToken();

        // DB에 토큰 저장
        userService.savePasswordResetToken(new PasswordResetTokenDTO(userEmail, resetToken, LocalDateTime.now()));

        // 재설정 링크 생성
        String localResetLink = "http://localhost:5173/auth/change-password/" + resetToken;
        String resetLink = "http://home-worry-party-alb-341952107.ap-northeast-2.elb.amazonaws.com/auth/change-password/" + resetToken;

        // 이메일 발송
        sendPasswordResetEmail(userEmail, resetLink, localResetLink);

        return userService.getemail(userEmail);
    }

    public Boolean passwordVerifyCheck(String password, Long userId) { // 사용자 이름과 비밀번호가 일치하는지 확인
        UserDTO member = userService.getUserById(userId);

        return passwordEncoder.matches(password, member.getPassword());
    }

    public String passwordVerify(Long userId) { // 비밀번호 재설정 토큰을 발급하고 토큰 정보 반환
        UserDTO member = userService.getUserById(userId);

        String resetToken = generateResetToken();

        userService.savePasswordResetToken(new PasswordResetTokenDTO(member.getEmail(), resetToken, LocalDateTime.now()));

        return resetToken;
    }

    @Transactional // 비밀번호 재설정
    public void PasswordRewrite(PasswordRewriteDTO dto){
        PasswordRewriteVO vo = dto.toVO();

        // 입력받은 토큰이 존재하는지를 확인
        if (!userService.existsByToken(vo.getToken())){
            throw new RuntimeException("토큰이 존재하지 않습니다: " + vo.getToken());
        }

        // 토큰이 만료되었는지 확인
        PasswordResetTokenDTO prt = userService.gettoken(vo.getToken());
        LocalDateTime now = LocalDateTime.now();
        if (!prt.getExpDate().isAfter(now)){
            throw new RuntimeException("토큰이 만료되었습니다: " + prt.getExpDate());
        }

        // user db의 비밀번호 변경
        UserDTO user = userService.getUserByEmail(prt.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.PasswordRewrite(user);

        // 사용된 토큰 정보 db에서 삭제
        userService.deleteToken(prt.getEmail());

    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    } // 고유 토큰 생성

    private void sendPasswordResetEmail(String toEmail, String link, String localLink) { // 이메일 발송
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("[집걱정단] 비밀번호 재설정 안내");

            String htmlContent = buildHtmlContent(link, localLink);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("이메일 발송에 실패했습니다.");
        }
    }

    private String buildHtmlContent(String link, String localLink) {
        String buttonStyle = "display: inline-block; padding: 12px 25px; font-size: 16px; color: #ffffff; background-color: #007bff; text-align: center; text-decoration: none; border-radius: 5px;";

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body style='font-family: Arial, sans-serif; text-align: center; padding: 40px;'>");
        sb.append("<div style='max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;'>");
        sb.append("<h2>비밀번호 재설정 요청</h2>");
        sb.append("<p>안녕하세요,</p>");
        sb.append("<p>아래 버튼을 클릭하여 비밀번호 재설정을 완료해주세요.</p>");
        sb.append("<br>");

        // 버튼 링크
        sb.append("<a href=\"").append(localLink).append("\" style=\"").append(buttonStyle).append("\">비밀번호 재설정하기</a>");

        sb.append("<br><br>");
        sb.append("<p>만약 버튼이 작동하지 않는다면, 아래 링크를 복사하여 브라우저에 붙여넣어 주세요:</p>");
        sb.append("<p style='word-break: break-all;'>").append(link).append("</p>");
        sb.append("<hr>");
        sb.append("<p style='font-size: 12px; color: #888;'>본인이 요청하지 않으셨다면 관리자에게 알려주세요.</p>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
}