package com.manhhoach.JavaTour.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingRequest {
    private int page = 1;
    private int size = 10;
}
