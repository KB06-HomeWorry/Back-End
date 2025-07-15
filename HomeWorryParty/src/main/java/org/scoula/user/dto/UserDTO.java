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
    private Long userId;         // user_id (PK)
    private String name;         // 이름
    private String email;        // 이메일
    private String password;     // 비밀번호(암호화 권장)
    private String phone;        // 휴대폰
    private String userType;     // 사용자 유형(고객/중개사 등)
    private Date regDate;        // 등록일 (있을 경우)
    private Date updateDate;     // 수정일 (있을 경우)

    private MultipartFile avatar;    // 프로필 이미지 등(필요시)

    // VO → DTO
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

    // DTO → VO
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
