package com.manhhoach.JavaTour.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private String username;
    private List<RoleDto> roles;
    private List<String> permissions;
}
