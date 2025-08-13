package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.common.PagedResponse;
import com.manhhoach.JavaTour.dto.req.CreateTourReq;
import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.entity.Tour;
import com.manhhoach.JavaTour.entity.TourImage;
import com.manhhoach.JavaTour.repository.TourImageRepository;
import com.manhhoach.JavaTour.repository.TourRepository;
import com.manhhoach.JavaTour.service.TourService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourImageRepository tourImageRepository;

    @Override
    public List<TourDto> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TourDto getTourById(Long id) {

        List<Object[]> rows = tourRepository.findTourWithImages(id);

        if (rows.isEmpty()) {
            throw new RuntimeException("Tour not found");
        }
        var tour = new TourDto();
        Object[] firstRow = rows.get(0);
        tour.setId(((Number) firstRow[0]).longValue());
        tour.setName((String) firstRow[1]);
        tour.setDescription((String) firstRow[2]);
        tour.setLocation((String) firstRow[3]);
        tour.setLatitude(firstRow[4] != null ? ((Number) firstRow[4]).doubleValue() : null);
        tour.setLongitude(firstRow[5] != null ? ((Number) firstRow[5]).doubleValue() : null);


        List<String> imageUrls = rows.stream()
                .map(r -> (String) r[6])
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        tour.setImageUrls(imageUrls);
        return tour;
    }

    @Override
    public TourDto createTour(CreateTourReq request) {
        // 1. Tạo đối tượng Tour từ request
        Tour tour = new Tour();
        tour.setName(request.getName());
        tour.setDescription(request.getDescription());
        tour.setLocation(request.getLocation());
        tour.setLatitude(request.getLatitude());
        tour.setLongitude(request.getLongitude());
        // 2. Lưu Tour trước để có id
        Tour savedTour = tourRepository.save(tour);

        // 3. Tạo danh sách TourImage từ imageUrls
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<TourImage> images = request.getImageUrls().stream()
                    .map(url -> {
                        TourImage img = new TourImage();
                        img.setTourId(savedTour.getId());
                        img.setImageUrl(url);
                        return img;
                    })
                    .toList();

            tourImageRepository.saveAll(images); // lưu tất cả ảnh
        }

        // 4. Trả về DTO
        return toDto(savedTour);
    }


    @Override
    @Transactional
    public TourDto updateTour(Long id, CreateTourReq request) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));

        tour.setName(request.getName());
        tour.setDescription(request.getDescription());
        tour.setLocation(request.getLocation());
        tour.setLatitude(request.getLatitude());
        tour.setLongitude(request.getLongitude());

        Tour updatedTour = tourRepository.save(tour);

        List<String> newImageUrls = request.getImageUrls() != null ? request.getImageUrls() : Collections.emptyList();

        // Lấy danh sách ảnh hiện tại của tour
        List<TourImage> existingImages = tourImageRepository.findByTourId(id);

        Set<String> existingUrls = existingImages.stream()
                .map(TourImage::getImageUrl)
                .collect(Collectors.toSet());

        Set<String> newUrls = new HashSet<>(newImageUrls);

        // 1. Xoá những ảnh có trong DB mà không có trong request
        List<TourImage> imagesToDelete = existingImages.stream()
                .filter(img -> !newUrls.contains(img.getImageUrl()))
                .collect(Collectors.toList());
        tourImageRepository.deleteAll(imagesToDelete);

        // 2. Thêm những ảnh mới trong request mà chưa có trong DB
        List<TourImage> imagesToAdd = newImageUrls.stream()
                .filter(url -> !existingUrls.contains(url))
                .map(url -> {
                    TourImage img = new TourImage();
                    img.setTourId(updatedTour.getId());
                    img.setImageUrl(url);
                    return img;
                })
                .collect(Collectors.toList());

        if (!imagesToAdd.isEmpty()) {
            tourImageRepository.saveAll(imagesToAdd);
        }

        return toDto(updatedTour);
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
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<Tour> tourPage = tourRepository.findAll(pageable);
        var tourIds = tourPage.getContent().stream().map(e -> e.getId()).toList();
        var imgList = tourImageRepository.findByListTourIds(tourIds);
        var tourDtos = tourPage.getContent().stream().map(tour -> {
            var dto = toDto(tour);
            dto.setImageUrls(
                    imgList.stream()
                            .filter(tourImage -> dto.getId().equals(tourImage.getTourId()))
                            .map(e -> e.getImageUrl())
                            .toList()
            );
            return dto;
        }).toList();

        return new PagedResponse<>(
                tourDtos,
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
        tour.setLatitude(tour.getLatitude());
        tour.setLongitude(tour.getLongitude());
        return dto;
    }
}
