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
    private Long userId;     // user_id (PK)
    private String username;     // 이름
    private String email;    // 이메일
    private String password; // 비밀번호(암호화 권장)
    private String phone;    // 휴대폰
    private String userType; // 사용자 유형(고객/중개사 등)
    private Date regDate;    // 등록일
    private Date updateDate; // 수정일

    // DB에 이미지 파일명(또는 URL) 컬럼이 있을 경우 추가
    private String avatarPath; // 프로필 이미지 경로(필요 시)

    private List<AuthVO> authList;
}
