package com.manhhoach.JavaTour.controller;


import com.manhhoach.JavaTour.common.ApiResponse;
import com.manhhoach.JavaTour.dto.req.CreateShortUrlReq;
import com.manhhoach.JavaTour.dto.res.TourDto;
import com.manhhoach.JavaTour.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/short-urls")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping
    public ApiResponse<String> createShortUrl(@RequestBody CreateShortUrlReq req) {
        return ApiResponse.success(shortUrlService.createShortUrl(req));
    }

    @GetMapping("{shortCode}")
    public ApiResponse<String> getOriginalUrl(@PathVariable String shortCode){
        return ApiResponse.success(shortUrlService.getOriginalUrl(shortCode));
    }
}
