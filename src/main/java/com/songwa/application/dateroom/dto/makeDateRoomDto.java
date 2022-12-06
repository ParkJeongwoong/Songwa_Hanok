package com.songwa.application.dateroom.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class makeDateRoomDto {
    private LocalDate date;
    private long roomId;
}
