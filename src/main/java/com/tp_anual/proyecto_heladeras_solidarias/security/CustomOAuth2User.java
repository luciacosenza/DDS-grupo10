package com.tp_anual.proyecto_heladeras_solidarias.security;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final Usuario usuario;
    private final OAuth2User oAuth2User;

    public CustomOAuth2User(Usuario vUsuario, OAuth2User vOAuth2User) {
        usuario = vUsuario;
        oAuth2User = vOAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(usuario.getRole()));
    }

    @Override
    public String getName() {   // Nombre + Apellido
        return oAuth2User.getName();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
