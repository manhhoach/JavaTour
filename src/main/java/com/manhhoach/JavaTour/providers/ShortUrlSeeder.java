package com.manhhoach.JavaTour.providers;

import com.manhhoach.JavaTour.entity.ShortUrl;
import com.manhhoach.JavaTour.repository.ShortUrlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ShortUrlSeeder implements CommandLineRunner {

    private final ShortUrlRepository shortUrlRepository;
    private final Random random = new Random();

    private static final String BASE_URL = "http://short.ly/";
    private static final String BASE62_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE62_LENGTH = BASE62_ALPHABET.length();

    public ShortUrlSeeder(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public void run(String... args) {
        if (shortUrlRepository.count() > 0) {
           return; // Skip if DB already has data
        }

        List<ShortUrl> batch = new ArrayList<>();

        for (int i = 10001; i < 1000000; i++) {
            String originalUrl = "https://example.com/page/" + i;
            String shortCode = randomBase62(6);

            ShortUrl shortUrl = ShortUrl.builder()
                    .originalUrl(originalUrl)
                    .shortCode(shortCode)
                    .shortUrl(BASE_URL + shortCode)
                    .expiryAt(LocalDateTime.now().plusDays(random.nextInt(365))) // random expiry in 1 year
                    .build();

            batch.add(shortUrl);
        }

        shortUrlRepository.saveAll(batch);
        System.out.println("✅ Inserted " + batch.size() + " short URLs");
    }

    private String randomBase62(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(BASE62_ALPHABET.charAt(random.nextInt(BASE62_LENGTH)));
        }
        return sb.toString();
    }
}