package com.example.websocketwebflux.model;

import com.example.websocketwebflux.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("room")
public class RoomModel extends BaseModel{

    @Column
    private String name;
    @Column
    private Long idCreator;
    @Column
    private Date createTime;
    @Column
    private int numberPlayer;
    @Column
    private String imageBackgroundUrl;
    @Column
    private String roleGame;
    @Column
    private Long typeGameId;
    @Column
    private String roomPassword;
    //private List<UserDTO> listPlayer;

}
