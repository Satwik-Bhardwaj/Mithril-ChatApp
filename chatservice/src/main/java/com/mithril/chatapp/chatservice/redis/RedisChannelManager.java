package com.mithril.chatapp.chatservice.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import static com.mithril.chatapp.chatservice.util.ChatConstants.WS_MSG_CHANNEL;

@Service
public class RedisChannelManager {

    @Value("${spring.application.instance-id}")
    private String instanceId;

    private final RedisMessageListenerContainer container;
    private final RedisMessageSubscriber redisMessageSubscriber;
    private final RedisTemplate<String, String> activeChannels = new RedisTemplate<>();

    public RedisChannelManager(RedisMessageListenerContainer container, RedisMessageSubscriber redisMessageSubscriber) {
        this.container = container;
        this.redisMessageSubscriber = redisMessageSubscriber;
    }

    public boolean isSubscribedUserChannel(String userId) {
        return Boolean.TRUE.equals(this.activeChannels.hasKey(WS_MSG_CHANNEL + userId));
    }

    /**
     * Subscribe to a user channel
     * @param userId The user id
     */
    public void subscribeUserChannel(String userId) {
        String channelName = WS_MSG_CHANNEL + userId;
        // Create a new channel topic with the user id
        ChannelTopic channelTopic = new ChannelTopic(channelName);
        // Add the message listener to the container
        this.container.addMessageListener(this.redisMessageSubscriber, channelTopic);
        // marking the channel as active with the instance id
        this.activeChannels.opsForValue().set(channelName, instanceId);
    }

    /**
     * Unsubscribe from a user channel
     * @param userId The user id
     */
    public void unsubscribeUserChannel(String userId) {
        String channelName = WS_MSG_CHANNEL + userId;

        if (Boolean.TRUE.equals(this.activeChannels.hasKey(channelName))) {
            // Remove the message listener from the container
            this.container.removeMessageListener(this.redisMessageSubscriber);
            // Remove the message listener from the container with the channel topic
            this.container.removeMessageListener(this.redisMessageSubscriber, new ChannelTopic(channelName));
            // Remove the channel from the active channels
            this.activeChannels.delete(channelName);
        }

    }
}
