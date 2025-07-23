package org.scoula.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.user.domain.PasswordResetTokenVO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetTokenDTO {
    String email;
    String token;
    LocalDateTime expDate;

    public static PasswordResetTokenDTO of(PasswordResetTokenVO p) {
        return PasswordResetTokenDTO.builder()
                .email(p.getEmail())
                .token(p.getToken())
                .expDate(p.getExpDate())
                .build();
    }

    public PasswordResetTokenVO toVO(){
        return PasswordResetTokenVO.builder()
                .email(email)
                .token(token)
                .expDate(expDate)
                .build();
    }
}
