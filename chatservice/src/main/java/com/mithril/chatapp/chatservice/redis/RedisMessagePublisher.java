package com.mithril.chatapp.chatservice.redis;

import com.mithril.chatapp.chatservice.dto.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, WebSocketMessage message) {
        redisTemplate.convertAndSend(channel, message);
        System.out.println("Publishing the message: " + message + " to channel: " + channel);
    }
}
