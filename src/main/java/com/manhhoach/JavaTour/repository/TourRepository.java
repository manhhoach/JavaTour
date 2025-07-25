package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query(value = """
    SELECT new com.manhhoach.JavaTour.dto.res.TourDto(
            t.id, t.name, t.description, t.location, 
           new com.manhhoach.JavaTour.entity.GeoLocation(t.coordinates.latitude, t.coordinates.longitude),
          null)
    FROM Tour t 
    WHERE t.id = :id
    """)
    TourDto getDetail(@Param("id") Long id);
}
