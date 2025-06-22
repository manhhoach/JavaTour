package com.manhhoach.JavaTour.dto.req;

import com.manhhoach.JavaTour.entity.GeoLocation;
import lombok.Data;

import java.time.LocalDate;


@Data
public class CreateTourReq {
    private String name;
    private String description;
    private String location;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;

    private GeoLocation coordinates;
}
