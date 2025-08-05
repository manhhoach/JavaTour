package com.manhhoach.JavaTour.controller;


import com.manhhoach.JavaTour.common.ApiResponse;
import com.manhhoach.JavaTour.config.annotations.IsAuthorized;
import com.manhhoach.JavaTour.dto.req.LoginReq;
import com.manhhoach.JavaTour.dto.req.RegisterReq;
import com.manhhoach.JavaTour.dto.res.LoginRes;
import com.manhhoach.JavaTour.dto.res.UserDto;
import com.manhhoach.JavaTour.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginRes> login(@RequestBody LoginReq req){
        return ApiResponse.success(authService.login(req));
    }

    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody RegisterReq req){
        return ApiResponse.success(authService.register(req));
    }

    @IsAuthorized({})
    @GetMapping("/me")
    public ApiResponse<UserDto> getMe(){
        return ApiResponse.success(authService.getMe());
    }
}
