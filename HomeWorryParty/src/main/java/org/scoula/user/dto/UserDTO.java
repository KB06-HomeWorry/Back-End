package org.scoula.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.security.account.domain.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String userType;
    private Date regDate;
    private Date updateDate;

    private MultipartFile avatar;

    public static UserDTO of(UserVO u) {
        return UserDTO.builder()
                .userId(u.getUserId())
                .name(u.getUsername())
                .email(u.getEmail())
                .password(u.getPassword())
                .phone(u.getPhone())
                .userType(u.getUserType())
                .regDate(u.getRegDate())
                .updateDate(u.getUpdateDate())
                .build();
    }

    public UserVO toVO() {
        return UserVO.builder()
                .userId(userId)
                .username(name)
                .email(email)
                .password(password)
                .phone(phone)
                .userType(userType)
                .regDate(regDate)
                .updateDate(updateDate)
                .build();
    }
}
