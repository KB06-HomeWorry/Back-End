package org.scoula.security.account.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserVO {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String userType;
    private Date regDate;
    private Date updateDate;

    private String avatarPath; // 프로필 이미지 경로(필요 시)

    private List<AuthVO> authList;
}
