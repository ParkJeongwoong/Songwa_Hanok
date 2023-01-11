package com.songwa.application.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    private long reservationId;
    private String dateRoomId;
    private long guestId;
}
