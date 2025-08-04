package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query(value = """
        SELECT 
            t.id AS tour_id,
            t.name,
            t.description,
            t.location,
            t.latitude,
            t.longitude,
            i.image_url
        FROM tour t
        LEFT JOIN tour_image i ON t.id = i.tour_id
        WHERE t.id = :id
    """, nativeQuery = true)
    List<Object[]> findTourWithImages(@Param("id") Long id);
}
