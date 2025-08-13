package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.dto.res.PermissionDto;
import com.manhhoach.JavaTour.dto.res.RoleDto;
import com.manhhoach.JavaTour.dto.res.UserDto;
import com.manhhoach.JavaTour.repository.UserRepository;
import com.manhhoach.JavaTour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getDetail(Long id) {
        var user = userRepository.getDetail(id).orElseThrow(() -> new RuntimeException());
        var roles = user.getRoles().stream().map(r -> {
            var permissions = r.getPermissions().stream().map(p ->
                    PermissionDto.builder().code(p.getCode()).build()
            ).toList();

            return RoleDto.builder().name(r.getName()).permissions(permissions).build();
        }).toList();


        var result = UserDto.builder()
                .username(user.getUsername())
                .roles(roles)
                .build();
        return result;
    }
}
