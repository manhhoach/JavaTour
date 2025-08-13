package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.constants.RoleConstant;
import com.manhhoach.JavaTour.dto.req.LoginReq;
import com.manhhoach.JavaTour.dto.req.RegisterReq;
import com.manhhoach.JavaTour.dto.res.LoginRes;
import com.manhhoach.JavaTour.dto.res.UserDto;
import com.manhhoach.JavaTour.entity.Role;
import com.manhhoach.JavaTour.entity.User;
import com.manhhoach.JavaTour.providers.JwtTokenProvider;
import com.manhhoach.JavaTour.providers.PasswordHasher;
import com.manhhoach.JavaTour.repository.RoleRepository;
import com.manhhoach.JavaTour.repository.UserRepository;
import com.manhhoach.JavaTour.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginRes login(LoginReq req) {
//        User user = userRepository.findByUsername(req.getUsername())
//                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
//        if (!passwordHasher.verify(req.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid username or password");
//        }
//        var listRole = user.getRoles().stream().map(e->e.getCode()).toList();
//        var token = jwtTokenProvider.generateToken(user.getUsername(), listRole);
//        return LoginRes.builder().token(token).build();

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        return LoginRes.builder().build();
    }

    @Override
    public boolean register(RegisterReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        String hashedPassword = passwordHasher.hash(req.getPassword());
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
        return null;
    }
}
