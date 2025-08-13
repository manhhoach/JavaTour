package com.manhhoach.JavaTour.config;

import com.manhhoach.JavaTour.filter.AuthEntryPoint;
import com.manhhoach.JavaTour.provider.JwtTokenProvider;
import com.manhhoach.JavaTour.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer")) {
                String token = header.substring(7);
                String username = jwtTokenProvider.getUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Set<String> permissions = userRepository.getPermissionsByUsername(username);
                    var grantedAuthorities = permissions.stream().map(e-> new SimpleGrantedAuthority(e)).toList();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            authEntryPoint.commence(request, response, ex);
        }
    }
}