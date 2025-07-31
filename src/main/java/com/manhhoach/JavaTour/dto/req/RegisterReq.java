package com.manhhoach.JavaTour.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
}
