package org.scoula.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    private UserVO member;

    public CustomUser(UserVO userVO) {
        super(userVO.getEmail(), userVO.getPassword(), userVO.getAuthList());
        this.member = userVO;
    }

    public CustomUser(String email, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }
}
