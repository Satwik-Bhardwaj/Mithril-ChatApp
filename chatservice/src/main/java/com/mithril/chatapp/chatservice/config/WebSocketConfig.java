package com.mithril.chatapp.chatservice.config;

import com.mithril.chatapp.chatservice.redis.RedisMessagePublisher;
import com.mithril.chatapp.chatservice.redis.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private RedisMessagePublisher redisRedisMessagePublisher;

    @Autowired
    private RedisMessageSubscriber redisSubscriber;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandler(this.webSocketSessionManager, this.redisRedisMessagePublisher, this.redisSubscriber), "/chat-ws/*")
                .addInterceptors(getParametersInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public HandshakeInterceptor getParametersInterceptor() {
        return new HandshakeInterceptor() {
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, java.util.Map<String, Object> attributes) throws Exception {
                String path = request.getURI().getPath();
                String userId = WebSocketHelper.getUserIdFromPath(path);
                attributes.put(WebSocketHelper.userIdKey, userId);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }
}