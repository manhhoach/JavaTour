package com.manhhoach.JavaTour.dto.res;

import com.manhhoach.JavaTour.entity.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TourDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private GeoLocation coordinates;
    private List<String> imageUrls;
}
