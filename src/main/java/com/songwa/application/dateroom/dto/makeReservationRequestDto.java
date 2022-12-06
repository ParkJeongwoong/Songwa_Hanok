package com.songwa.application.dateroom.dto;

import com.songwa.domain.Guest;
import lombok.Getter;

@Getter
public class makeReservationRequestDto {
    private String dateRoomId;
    protected String guestName;

    public Guest makeGuest() {
//        return Guest.builder().name(guestName).build();
        return new Guest(guestName);
    }
}
