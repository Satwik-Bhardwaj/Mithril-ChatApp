package com.mithril.chatapp.chatservice.redis;

import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, String message){
        redisTemplate.convertAndSend(channel, message);
        log.info("Publishing the message: {} to channel: {}", message, channel);
    }
}
