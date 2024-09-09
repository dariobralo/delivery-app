package com.delivery_api.utils.security;

import com.delivery_api.persistence.model.Rol;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final Rol rol;

    @Override
    public String getAuthority() {
        return rol.getRol().toString();
    }
}
