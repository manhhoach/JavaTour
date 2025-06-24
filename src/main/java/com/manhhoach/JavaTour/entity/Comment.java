package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private Long userId; // Người đăng comment

    @Column(nullable = false)
    private Long tourId; // Comment thuộc về tour nào

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Long parentId; // Nếu là reply thì chứa comment cha

}
