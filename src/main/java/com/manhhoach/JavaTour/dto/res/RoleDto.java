package com.manhhoach.JavaTour.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class RoleDto {
    private String code;
    private String name;
    private List<PermissionDto> permissions;
}
