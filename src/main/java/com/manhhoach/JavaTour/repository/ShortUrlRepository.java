package com.manhhoach.JavaTour.repository;

import com.manhhoach.JavaTour.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    @Query("""
            SELECT shortUrl FROM ShortUrl su WHERE su.originalUrl = :longUrl
            """)
    String findShortUrlByLongUrl(String longUrl);

    @Query("""
            SELECT COUNT(su) FROM ShortUrl su WHERE su.shortCode = :alias
            """)
    Integer countUrlWithAlias(String alias);
}
