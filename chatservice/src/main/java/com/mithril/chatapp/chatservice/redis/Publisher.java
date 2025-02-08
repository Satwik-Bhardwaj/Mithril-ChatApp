package com.mithril.chatapp.chatservice.redis;

import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Publisher {

    RedisClient client;

    @Autowired
    public Publisher(RedisClient client){
        this.client = client;
    }

    public void publish(String channel, String message){
        log.info("going to publish the message to channel {} and message = {}", channel, message);
        var connection = this.client.connect();
        connection.sync().publish(channel, message);
    }
}
