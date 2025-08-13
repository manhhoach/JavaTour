package com.manhhoach.JavaTour.service;

import com.manhhoach.JavaTour.dto.req.CreateShortUrlReq;

public interface ShortUrlService {
    String createShortUrl(CreateShortUrlReq req);

    String getOriginalUrl(String shortCode);
}
