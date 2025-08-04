package com.manhhoach.JavaTour.dto.req;

import lombok.Data;

import java.util.List;


@Data
public class CreateTourReq {
    private String name;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private List<String> imageUrls;
}
