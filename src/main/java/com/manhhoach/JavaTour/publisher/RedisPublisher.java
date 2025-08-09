package com.manhhoach.JavaTour.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public void publish(String message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
