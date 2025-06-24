package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class TourImage extends BaseEntity {
    @Column(nullable = false)
    private Long tourId; // Foreign key đến Tour (tự join khi cần)

    private String imageUrl;
}