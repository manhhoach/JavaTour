package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Tour extends BaseEntity {
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    private Double latitude;

    private Double longitude;
}
