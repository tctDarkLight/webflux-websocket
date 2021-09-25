package com.example.websocketwebflux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO{

    @JsonProperty("username")
    private String username;

    @JsonProperty("avatar")
    private String avatar;

    private String fullName;

    private String email;

    private String role;

}
