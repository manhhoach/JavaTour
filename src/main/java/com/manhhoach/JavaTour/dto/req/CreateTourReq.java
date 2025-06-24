package com.manhhoach.JavaTour.dto.req;

import com.manhhoach.JavaTour.entity.GeoLocation;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class CreateTourReq {
    private String name;
    private String description;
    private String location;
    private GeoLocation coordinates;
    private List<String> imageUrls;
}
