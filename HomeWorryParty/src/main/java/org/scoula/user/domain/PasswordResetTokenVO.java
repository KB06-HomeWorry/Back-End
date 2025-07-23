package org.scoula.user.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PasswordResetTokenVO {
    private String email;
    private String token;
    private LocalDateTime expDate;
}
