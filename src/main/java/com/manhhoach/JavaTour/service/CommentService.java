package com.manhhoach.JavaTour.service;

import com.manhhoach.JavaTour.dto.res.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentByTour(Long tourId);
}
