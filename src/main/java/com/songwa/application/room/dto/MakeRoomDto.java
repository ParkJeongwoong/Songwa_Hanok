package com.songwa.application.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MakeRoomDto {
    String name;

    @Builder
    public MakeRoomDto(String name) {
        this.name = name;
    }
}
