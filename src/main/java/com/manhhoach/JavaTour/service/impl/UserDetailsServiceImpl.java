package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.config.CustomUserDetails;
import com.manhhoach.JavaTour.repository.PermissionRepository;
import com.manhhoach.JavaTour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Set<String> permissions = permissionRepository.getPermissionsByUserId(user.getId());
        Set<GrantedAuthority> grantedAuthorities = permissions.stream().map(e -> new SimpleGrantedAuthority(e)).collect(Collectors.toSet());
        return new CustomUserDetails(user.getId(), username, user.getPassword(), grantedAuthorities);
    }
}
