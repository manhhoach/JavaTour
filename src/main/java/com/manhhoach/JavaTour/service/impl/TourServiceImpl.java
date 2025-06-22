package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.dto.req.CreateTourReq;
import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.repository.TourRepository;
import com.manhhoach.JavaTour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public List<TourDto> getAllTours() {
        return List.of();
    }

    @Override
    public TourDto getTourById(Long id) {
        return null;
    }

    @Override
    public TourDto createTour(CreateTourReq request) {
        return null;
    }

    @Override
    public TourDto updateTour(Long id, CreateTourReq request) {
        return null;
    }

    @Override
    public void deleteTour(Long id) {

    }
}
