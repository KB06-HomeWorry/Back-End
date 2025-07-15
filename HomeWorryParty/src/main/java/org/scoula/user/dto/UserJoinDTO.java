package org.scoula.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.security.account.domain.UserVO;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinDTO {
    private String username;
    private String password;
    private String email;

    private MultipartFile avatar;

    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}