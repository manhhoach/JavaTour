package com.manhhoach.JavaTour.service;

import com.manhhoach.JavaTour.dto.req.LoginReq;
import com.manhhoach.JavaTour.dto.req.RegisterReq;
import com.manhhoach.JavaTour.dto.res.LoginRes;
import com.manhhoach.JavaTour.dto.res.UserDto;

public interface AuthService {
    LoginRes login(LoginReq req);

    boolean register(RegisterReq req);

    UserDto getMe();
}
