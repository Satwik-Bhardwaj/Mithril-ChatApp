package com.mithril.wsg.wsservice.service.interfaces;

public interface GatewayRegistry {

    void addGatewaySessionMapping(String sessionId);

    void removeGatewaySessionMapping(String sessionId);
}
