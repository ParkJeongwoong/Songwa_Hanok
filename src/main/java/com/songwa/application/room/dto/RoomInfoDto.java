package com.songwa.application.room.dto;

import com.songwa.domain.HanokRoom;
import lombok.Getter;

@Getter
public class RoomInfoDto {
    private final long roomId;
    private final String roomName;

    public RoomInfoDto(HanokRoom entity) {
        this.roomId = entity.getId();
        this.roomName = entity.getName();
    }
}
