package com.manhhoach.JavaTour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Comment extends BaseEntity {

    @Column(nullable = false)
    Long userId; // Người đăng comment

    @Column(nullable = false)
    Long tourId; // Comment thuộc về tour nào

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    Long parentId; // Nếu là reply thì chứa comment cha

}
