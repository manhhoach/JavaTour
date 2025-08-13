package com.manhhoach.JavaTour.controller;

import com.manhhoach.JavaTour.common.ApiResponse;
import com.manhhoach.JavaTour.dto.res.UserDto;
import com.manhhoach.JavaTour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @GetMapping("/{id}")
    public ApiResponse<UserDto> getById(@PathVariable Long id) {
        return ApiResponse.success(userService.getDetail(id));
    }

}
