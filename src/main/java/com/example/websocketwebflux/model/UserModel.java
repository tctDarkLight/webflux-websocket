package com.example.websocketwebflux.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class UserModel extends BaseModel {

    //@JsonProperty("username")
    @Column
    private String username;

    //@JsonProperty("avatar")
    @Column
    private String avatar;

    @Column
    private String password;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String role;
}
