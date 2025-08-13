package com.manhhoach.JavaTour.entity;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Permission extends BaseEntity {
    private String name;
    private String code;
}
