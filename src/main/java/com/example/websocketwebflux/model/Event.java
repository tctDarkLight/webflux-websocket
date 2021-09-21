package com.example.websocketwebflux.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    public enum Type {
        CHAT_MESSAGE, USER_JOINED, USER_STATS, USER_LEFT
    }

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @Builder.Default
    private final int id = ID_GENERATOR.incrementAndGet();

    @JsonProperty("payload")
    private Payload payload;

    @Builder.Default
    private final long timestamp = System.currentTimeMillis();

    @JsonProperty("type")
    private Type type;

    //Initialize block
    {

    }

}
