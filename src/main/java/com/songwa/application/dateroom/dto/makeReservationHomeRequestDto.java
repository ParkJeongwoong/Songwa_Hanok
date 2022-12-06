package com.songwa.application.dateroom.dto;

import com.songwa.domain.Guest;
import com.songwa.domain.GuestHome;
import lombok.Getter;


@Getter
public class makeReservationHomeRequestDto extends makeReservationRequestDto {
    private String phoneNumber;

    public Guest makeGuest() {
        return GuestHome.builder()
                .name(super.guestName)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
