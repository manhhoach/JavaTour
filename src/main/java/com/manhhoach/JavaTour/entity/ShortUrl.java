package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(
        indexes = {
        @Index(name = "idx_short_code", columnList = "shortCode", unique = true)
})
public class ShortUrl extends BaseEntity {
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime expiryAt;
    private String shortCode;
}
