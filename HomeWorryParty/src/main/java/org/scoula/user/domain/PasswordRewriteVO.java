package org.scoula.user.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PasswordRewriteVO {
    private String token;
    private String password;
}
