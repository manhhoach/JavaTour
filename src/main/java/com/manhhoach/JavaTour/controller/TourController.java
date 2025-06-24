package com.manhhoach.JavaTour.controller;

import com.manhhoach.JavaTour.common.ApiResponse;
import com.manhhoach.JavaTour.common.PagedResponse;
import com.manhhoach.JavaTour.common.PagingRequest;
import com.manhhoach.JavaTour.dto.req.CreateTourReq;
import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    public ApiResponse<List<TourDto>> getAllTours() {
        return ApiResponse.success(tourService.getAllTours());
    }

    @GetMapping("/paged")
    public ApiResponse<PagedResponse<TourDto>> getPagedTours(PagingRequest request) {
        return ApiResponse.success(
                tourService.getToursPaged(request.getPage(), request.getSize())
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<TourDto> getTourById(@PathVariable Long id) {
        return ApiResponse.success(tourService.getTourById(id));
    }

    @PostMapping
    public ApiResponse<TourDto> createTour(@RequestBody CreateTourReq request) {
        return ApiResponse.success(tourService.createTour(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TourDto> updateTour(@PathVariable Long id, @RequestBody CreateTourReq request) {
        return ApiResponse.success(tourService.updateTour(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ApiResponse.success(null);
    }
}

