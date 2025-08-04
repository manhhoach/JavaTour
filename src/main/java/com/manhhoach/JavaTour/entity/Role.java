package com.manhhoach.JavaTour.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
public class Role extends BaseEntity{
    private String name;
    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="role_permission",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="permission_id")
    )
    private Set<Permission> permissions;
}
