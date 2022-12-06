package com.songwa.application.dateroom.dto;

import com.songwa.domain.DateRoom;
import com.songwa.domain.HanokRoom;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateRoomInfoDto {
    private final String dateRoomId;
    private final LocalDate date;
    private final long roomId;
    private final String roomName;
    private final long reservationState;

    public DateRoomInfoDto(DateRoom entity) {
        HanokRoom hanokRoom = entity.getHanokRoom();

        this.dateRoomId = entity.getDateRoomId();
        this.date = entity.getDate();
        this.roomId = hanokRoom.getId();
        this.roomName = hanokRoom.getName();
        this.reservationState = entity.getReservationState();
    }
}
