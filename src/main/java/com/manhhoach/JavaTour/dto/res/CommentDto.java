package com.manhhoach.JavaTour.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentDto {
    Long id;

    Long userId;

    String username;

    Long tourId; // Comment thuộc về tour nào

    String content;

    Long parentId; // Nếu là reply thì chứa comment cha

    List<CommentDto> replies;
}
