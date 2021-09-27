package com.example.websocketwebflux.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("type_game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeGame extends BaseModel{
    @Column
    private String name;
    @Column
    private String description;
}
