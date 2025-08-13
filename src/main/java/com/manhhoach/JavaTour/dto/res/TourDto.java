package com.manhhoach.JavaTour.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TourDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private List<String> imageUrls;
}
