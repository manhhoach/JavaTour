package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class GeoLocation {
    private Double latitude;
    private Double longitude;
}
