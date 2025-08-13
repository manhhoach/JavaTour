package com.manhhoach.JavaTour.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Long id;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;

    }

}
