package com.manhhoach.JavaTour.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class LoginRes {
    private String token;
    private String username;
}
