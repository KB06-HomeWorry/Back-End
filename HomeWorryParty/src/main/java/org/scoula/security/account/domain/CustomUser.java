package org.scoula.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    //Security내에서 회원정보를 담을 객체는 User객체임.
    //우리의 회원정보는 MemberVO에 있음.
    //MemberVO --> User객체에 매핑시켜주어야함.

    private UserVO member;

    //생성자 2개를 만들어줌.
    public CustomUser(UserVO userVO) {
        super(userVO.getUsername(), userVO.getPassword(), userVO.getAuthList());
        this.member = userVO;
    }

    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
