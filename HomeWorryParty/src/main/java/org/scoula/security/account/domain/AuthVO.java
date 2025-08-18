package org.scoula.security.account.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class AuthVO implements GrantedAuthority {

    private Long authId;
    private Long userId;
    private String auth;

    @Override
    public String getAuthority() {
        return auth;
    }
}
