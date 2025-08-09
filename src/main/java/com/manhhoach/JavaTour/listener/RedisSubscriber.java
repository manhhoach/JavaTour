package com.manhhoach.JavaTour.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern){
        System.out.println("Received: "+message.toString());
        System.out.println("Body: "+message.getBody());
        System.out.println("Channel: "+message.getChannel());
    }
}
