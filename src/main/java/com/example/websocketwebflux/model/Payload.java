package com.example.websocketwebflux.model;

import com.example.websocketwebflux.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    @JsonProperty("user")
    private UserDTO userDTO;

    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();
}
