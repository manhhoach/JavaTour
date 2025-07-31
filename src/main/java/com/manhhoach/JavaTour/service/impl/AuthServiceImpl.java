package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.dto.req.LoginReq;
import com.manhhoach.JavaTour.dto.req.RegisterReq;
import com.manhhoach.JavaTour.dto.res.LoginRes;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordHasher passwordHasher;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginRes login(LoginReq req) {
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if (!passwordHasher.verify(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return null;
    }

    @Override
    public boolean register(RegisterReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        String hashedPassword = passwordHasher.hash(req.getPassword());
        Role defaultRole = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Role mặc định không tồn tại"));

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(hashedPassword);
        user.setRoles(Set.of(defaultRole)); // dùng Set hoặc List tùy entity bạn

        // 5. Lưu user vào DB
        userRepository.save(user);
        return true;
    }
}
