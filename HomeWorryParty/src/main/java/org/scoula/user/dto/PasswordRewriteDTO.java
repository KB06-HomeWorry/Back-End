package org.scoula.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.user.domain.PasswordRewriteVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordRewriteDTO {
    private String token;
    private String password;

    public PasswordRewriteVO toVO() {
        return PasswordRewriteVO.builder()
                .token(token)
                .password(password)
                .build();
    }
}
