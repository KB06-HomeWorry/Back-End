package org.scoula.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.security.account.domain.UserVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String userType;


    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .userType(userType)
                .build();
    }
}