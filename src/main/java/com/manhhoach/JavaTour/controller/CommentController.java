package com.manhhoach.JavaTour.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {
//    @Autowired
//    private CommentService commentService;
//
//    @GetMapping("/{tourId}")
//    public ApiResponse<List<CommentDto>> getData(@PathVariable Long tourId) {
//        return ApiResponse.success(commentService.getCommentByTour(tourId));
//    }
}
