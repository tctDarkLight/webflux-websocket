package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.model.Event;
import com.example.websocketwebflux.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.io.IOException;

@Component
public class ChatSocketHandler implements WebSocketHandler {
    private final static int numberOfCacheMessage = 25;
    private final Sinks.Many<Event> sink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    private final Flux<Event> outputMessages = sink.asFlux().cache(numberOfCacheMessage);
    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService;

    @Value("${springbootwebfluxjjwt.token.prefix}")
    private String tokenPrefix;

    public ChatSocketHandler(AuthService authService) {
        this.authService = authService;
    }

    /**
     * @param session
     * @return
     */
    @Override
    public Mono<Void> handle(WebSocketSession session) {

        //Getting the token from header of request
        String authToken = session.getHandshakeInfo().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authToken != null && !authToken.isEmpty() && authToken.startsWith(tokenPrefix)) {
            authToken = authToken.substring(tokenPrefix.length());
        }

        String username = authService.getUsernameFromToken(authToken);
        if (username == null || username.isBlank()) {
            return session.close();
        }
        session.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .map(message -> {
                Event event = toEvent(message);
                event.getPayload().getUserDTO().setUsername(username);
                return event;
            })
            .subscribe(
                sink::tryEmitNext,
                sink::tryEmitError
            );

        return session.send
            (
                outputMessages.map(this::toJson)
                    .map(session::textMessage).log()
            );
    }

    private Event toEvent(String json) {
        try {
            return mapper.readValue(json, Event.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON:" + json, e);
        }
    }

    private String toJson(Event event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
