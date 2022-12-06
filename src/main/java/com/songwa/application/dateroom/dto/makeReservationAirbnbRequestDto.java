package com.songwa.application.dateroom.dto;

import com.songwa.domain.Guest;
import com.songwa.domain.GuestAirbnb;
import lombok.Getter;

@Getter
public class makeReservationAirbnbRequestDto extends makeReservationRequestDto {

    public Guest makeGuest() {
        return GuestAirbnb.builder().name(guestName).build();
    }

}
