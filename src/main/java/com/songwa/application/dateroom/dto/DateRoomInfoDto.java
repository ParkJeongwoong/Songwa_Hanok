package com.songwa.application.dateroom.dto;

import com.songwa.domain.DateRoom;
import com.songwa.domain.Room;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateRoomInfoDto {
    private final LocalDate date;
    private final long roomId;
    private final String roomName;
    private final long price;
    private final long reservationState;

    public DateRoomInfoDto(DateRoom entity) {
        Room room = entity.getRoom();

        this.date = entity.getDate();
        this.roomId = room.getId();
        this.roomName = room.getName();
        this.price = entity.getReal_price();
        this.reservationState = entity.getReservationState();
    }
}
