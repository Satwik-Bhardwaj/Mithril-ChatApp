package com.mithril.chatapp.chatservice.redis;

import com.mithril.chatapp.chatservice.config.WebSocketSessionManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

    RedisClient client;

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    private RedisPubSubCommands<String, String> sync;

    @Autowired
    public Subscriber(WebSocketSessionManager webSocketSessionManager, RedisClient client){
        this.webSocketSessionManager = webSocketSessionManager;
        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
        var redisListener = new SubscriberHelper(this.webSocketSessionManager);
        connection.addListener(redisListener);
        this.sync = connection.sync();
    }

    public void subscribe(String channel){
        this.sync.subscribe(channel);
    }

    public void  unsubscribe(String channel){
        this.sync.unsubscribe(channel);
    }
}
