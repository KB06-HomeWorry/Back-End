package org.scoula.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.security.account.domain.UserVO;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    MultipartFile avatar;

    private String username;
    private String password;
    private String email;

    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
                .email(email)
                .build();
    }
}
