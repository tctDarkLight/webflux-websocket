package com.example.websocketwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class UserModel extends BaseModel {

    //@JsonProperty("username")
    @Column
    @Min(value = 4)
    private String username;

    //@JsonProperty("avatar")
    @Column
    private String avatar;

    @Column
    //@ValidPassword
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Password is invalid")
    private String password;

    @Column
    private String fullName;

    @Column
    @Email
    private String email;

    @Column
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    @Column
    private String role;
}
