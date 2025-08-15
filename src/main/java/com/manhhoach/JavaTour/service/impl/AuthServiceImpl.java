package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.config.CustomUserDetails;
import com.manhhoach.JavaTour.constant.RoleConstant;
import com.manhhoach.JavaTour.dto.req.LoginReq;
import com.manhhoach.JavaTour.dto.req.RefreshTokenReq;
import com.manhhoach.JavaTour.dto.req.RegisterReq;
import com.manhhoach.JavaTour.dto.res.LoginRes;
import com.manhhoach.JavaTour.dto.res.UserDto;
import com.manhhoach.JavaTour.entity.Role;
import com.manhhoach.JavaTour.entity.User;
import com.manhhoach.JavaTour.provider.JwtTokenProvider;
import com.manhhoach.JavaTour.repository.PermissionRepository;
import com.manhhoach.JavaTour.repository.RoleRepository;
import com.manhhoach.JavaTour.repository.UserRepository;
import com.manhhoach.JavaTour.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final AuthenticationManager authenticationManager;
    private final PermissionRepository permissionRepository;

    @Value("${security.jwt.access-token.key}")
    private String accessTokenKey;

    @Value("${security.jwt.access-token.expiration}")
    private long accessTokenExpirationMs;

    @Value("${security.jwt.refresh-token.key}")
    private String refreshTokenKey;

    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshTokenExpirationMs;

    @Override
    public LoginRes login(LoginReq req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<String> permissions = userDetails.getAuthorities().stream().map(e -> e.getAuthority()).toList();
            return buildLoginRes(userDetails.getId(),userDetails.getUsername(),permissions);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean register(RegisterReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        String hashedPassword = passwordEncoder.encode(req.getPassword());
        Role defaultRole = roleRepository.findByName(RoleConstant.USER)
                .orElseThrow(() -> new RuntimeException("Default role is not exists"));

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(hashedPassword);
        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto getMe() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString();
        var permissions = authentication.getAuthorities().stream().map(e -> e.getAuthority()).toList();
        return UserDto.builder().username(username).permissions(permissions).build();
    }

    @Override
    public LoginRes refreshToken(RefreshTokenReq req) {
        String username = jwtTokenProvider.getUsername(req.getRefreshToken(), refreshTokenKey);
        var id = jwtTokenProvider.getId(req.getRefreshToken(), refreshTokenKey);
        var permissions = permissionRepository.getPermissionsByUserId(id);
        return buildLoginRes(id, username, permissions.stream().toList());
    }

    private LoginRes buildLoginRes(Long id, String username, List<String> permissions ){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("permissions", permissions);

        String accessToken = jwtTokenProvider.generateToken(username, claims, accessTokenKey, accessTokenExpirationMs);
        String refreshToken = jwtTokenProvider.generateToken(username, claims, refreshTokenKey, refreshTokenExpirationMs);

        return LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(username)
                .build();
    }
}
