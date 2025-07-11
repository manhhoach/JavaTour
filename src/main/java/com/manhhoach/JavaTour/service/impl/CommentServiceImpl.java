package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.dto.res.CommentDto;
import com.manhhoach.JavaTour.repository.CommentRepository;
import com.manhhoach.JavaTour.repository.TourRepository;
import com.manhhoach.JavaTour.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<CommentDto> getCommentByTour(Long tourId) {
        var data = commentRepository.findByTourId(tourId);
        return data;
    }
}
