package com.mithril.chatapp.chatservice.redis;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisChannelManager {

    public static final String CHANNEL = "chat:user:";
    private final RedisMessageListenerContainer container;
    private final RedisMessageSubscriber redisMessageSubscriber;
    private final Map<String, ChannelTopic> activeChannels = new ConcurrentHashMap<>();

    public RedisChannelManager(RedisMessageListenerContainer container, RedisMessageSubscriber redisMessageSubscriber) {
        this.container = container;
        this.redisMessageSubscriber = redisMessageSubscriber;
    }

    public void subscribeUserChannel(String userId) {
        ChannelTopic channelTopic = new ChannelTopic(CHANNEL + userId);
        this.container.addMessageListener(this.redisMessageSubscriber, channelTopic);
        this.activeChannels.put(CHANNEL + userId, channelTopic);
    }

    public void unsubscribeUserChannel(String userId) {
        ChannelTopic channelTopic = this.activeChannels.remove(CHANNEL + userId);
        if (channelTopic != null) {
            this.container.removeMessageListener(this.redisMessageSubscriber);
            this.container.removeMessageListener(this.redisMessageSubscriber, channelTopic);
        }
    }


}
