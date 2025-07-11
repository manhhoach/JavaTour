package com.manhhoach.JavaTour.controller;


import com.manhhoach.JavaTour.common.ApiResponse;
import com.manhhoach.JavaTour.dto.res.CommentDto;
import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{tourId}")
    public ApiResponse<List<CommentDto>> getData(@PathVariable Long tourId) {
        return ApiResponse.success(commentService.getCommentByTour(tourId));
    }
}
