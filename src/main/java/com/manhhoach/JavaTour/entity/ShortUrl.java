package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class ShortUrl extends BaseEntity {
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime expiryAt;
    private String shortCode;
}
