package com.mithril.wsg.wsservice.service.implementations;

import com.mithril.wsg.wsservice.service.interfaces.GatewayRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GatewayRegistryImpl implements GatewayRegistry {

    private static final String GATEWAY_SESSIONS_KEY = "gateway_sessions";

    private static final String GATEWAY_ID = System.getenv("HOSTNAME");

    private final RedisTemplate<String, Object> redisTemplate;

    public GatewayRegistryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addGatewaySessionMapping(String sessionId) {
        log.info("Adding gateway session mapping for sessionId: {} with gateway: {} to redis.", sessionId, GATEWAY_ID);
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(GATEWAY_SESSIONS_KEY, sessionId, GATEWAY_ID);
        log.info("Added gateway session mapping for sessionId: {} with gateway: {} to redis.", sessionId, GATEWAY_ID);
    }

    @Override
    public void removeGatewaySessionMapping(String sessionId) {
        log.info("Removing gateway session mapping for sessionId: {} from redis.", sessionId);
        redisTemplate.opsForHash().delete(GATEWAY_SESSIONS_KEY, sessionId);
        log.info("Removed gateway session mapping for sessionId: {} from redis.", sessionId);
    }
}
