package com.manhhoach.JavaTour.controller;

import com.manhhoach.JavaTour.publisher.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pubsub")
@RequiredArgsConstructor
public class PubSubController {
    private final RedisPublisher publisher;

    @GetMapping("/publish")
    public String publishMessage(@RequestParam String message) {
        publisher.publish(message);
        return "Message sent: " + message;
    }
}
