package com.example.websocketwebflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveWebSocketConfig {

    private WebSocketHandler webSocketHandler;

    public ReactiveWebSocketConfig(ChatSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    @Bean
    public HandlerMapping webSocketMapping(/*EventPublisher eventPublisher, ObjectMapper mapper*/) {
        Map<String, Object> map = new HashMap<>();
        map.put("/websocket/chat", webSocketHandler);
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(map);

        //Without the order things break :-/
        simpleUrlHandlerMapping.setOrder(-1);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }


}
