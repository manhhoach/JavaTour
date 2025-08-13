package com.manhhoach.JavaTour.service;

import com.manhhoach.JavaTour.common.PagedResponse;
import com.manhhoach.JavaTour.dto.req.CreateTourReq;
import com.manhhoach.JavaTour.dto.res.TourDto;

import java.util.List;


public interface TourService {
    List<TourDto> getAllTours();

    TourDto getTourById(Long id);

    TourDto createTour(CreateTourReq request);

    TourDto updateTour(Long id, CreateTourReq request);

    void deleteTour(Long id);

    PagedResponse<TourDto> getToursPaged(int page, int size);
}
