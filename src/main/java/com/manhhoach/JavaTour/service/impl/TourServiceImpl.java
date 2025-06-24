package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.common.PagedResponse;
import com.manhhoach.JavaTour.dto.req.CreateTourReq;
import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.entity.Tour;
import com.manhhoach.JavaTour.repository.TourRepository;
import com.manhhoach.JavaTour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public List<TourDto> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TourDto getTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
        return toDto(tour);
    }

    @Override
    public TourDto createTour(CreateTourReq request) {
        Tour tour = new Tour();
        tour.setName(request.getName());
        tour.setDescription(request.getDescription());
        tour.setLocation(request.getLocation());
        tour.setCoordinates(request.getCoordinates());

        Tour saved = tourRepository.save(tour);
        return toDto(saved);
    }

    @Override
    public TourDto updateTour(Long id, CreateTourReq request) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));

        tour.setName(request.getName());
        tour.setDescription(request.getDescription());
        tour.setLocation(request.getLocation());
        tour.setCoordinates(request.getCoordinates());

        Tour updated = tourRepository.save(tour);
        return toDto(updated);
    }

    @Override
    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new RuntimeException("Tour not found with id: " + id);
        }
        tourRepository.deleteById(id);
    }

    @Override
    public PagedResponse<TourDto> getToursPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<Tour> tourPage = tourRepository.findAll(pageable);

        return new PagedResponse<>(
                tourPage.getContent().stream().map(this::toDto).toList(),
                tourPage.getNumber() + 1,
                tourPage.getTotalPages(),
                tourPage.getTotalElements()
        );
    }

    private TourDto toDto(Tour tour) {
        TourDto dto = new TourDto();
        dto.setId(tour.getId());
        dto.setName(tour.getName());
        dto.setDescription(tour.getDescription());
        dto.setLocation(tour.getLocation());
        dto.setCoordinates(tour.getCoordinates());
        return dto;
    }
}
