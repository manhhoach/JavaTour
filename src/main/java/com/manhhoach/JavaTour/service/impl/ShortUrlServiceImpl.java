package com.manhhoach.JavaTour.service.impl;

import com.manhhoach.JavaTour.dto.req.CreateShortUrlReq;
import com.manhhoach.JavaTour.entity.ShortUrl;
import com.manhhoach.JavaTour.helpers.StringGenerate;
import com.manhhoach.JavaTour.helpers.UrlHelper;
import com.manhhoach.JavaTour.repository.ShortUrlRepository;
import com.manhhoach.JavaTour.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;


    @Transactional
    @Override
    public String createShortUrl(CreateShortUrlReq req) {
        if (!UrlHelper.isValidUrl(req.getLongUrl())) {
            throw new RuntimeException("url is invalid");
        }
        String shortUrl = shortUrlRepository.findShortUrlByLongUrl(req.getLongUrl());
        if (shortUrl != null) {
            return shortUrl;
        }
        ShortUrl shortUrlEntity = null;
        if (req.getAlias() != null && req.getAlias() != "") {
            Integer countOfUrl = shortUrlRepository.countUrlWithAlias(req.getAlias());
            if (countOfUrl > 0) {
                throw new RuntimeException("Alias has already existed");
            }
            shortUrlEntity = ShortUrl.builder()
                    .originalUrl(req.getLongUrl())
                    .expiryAt(req.getExpirationDate())
                    .shortCode(req.getAlias())
                    .shortUrl(combineShortUrl(req.getAlias()))
                    .build();
        }
        else
        {
            shortUrlEntity = ShortUrl.builder().originalUrl(req.getLongUrl()).expiryAt(req.getExpirationDate()).build();
            shortUrlRepository.save(shortUrlEntity);

            String shortCode = StringGenerate.generateShortCode(shortUrlEntity.getId());
            shortUrlEntity.setShortCode(shortCode);
            shortUrlEntity.setShortUrl(combineShortUrl(shortCode));

        }

        shortUrlRepository.save(shortUrlEntity);
        return shortUrlEntity.getShortUrl();
    }

    String combineShortUrl(String shortCode) {
        String baseUrl = "http://localhost:3000/api/short-urls";
        return String.format("%s/%s", baseUrl, shortCode);
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        return "";
    }
}
