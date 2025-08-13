package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.dto.res.CommentDto;
import com.manhhoach.JavaTour.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = """
            SELECT c.id, c.content, u.username FROM comment c JOIN user ON c.userId = user.id
            WHERE c.tourId = :tourId
            """, nativeQuery = true)
    List<CommentDto> findByTourId(Long tourId);


//    @Query("""
//            SELECT c.id, c.content, u.username FROM comment c JOIN user ON c.userId = user.id
//            WHERE c.parentId = :parentId
//            """)
//    List<CommentDto> findByParentId(Long parentId);
}
