package com.manhhoach.JavaTour.controller;

import com.manhhoach.JavaTour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tours")
public class TourController {

    @Autowired
    TourService tourService;


}
