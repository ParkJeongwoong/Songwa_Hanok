package com.songwa.adapter.controller;

import com.songwa.application.common.dto.GeneralResponseDto;
import com.songwa.application.reservation.dto.MakeReservationAirbnbRequestDto;
import com.songwa.application.reservation.dto.MakeReservationHomeRequestDto;
import com.songwa.application.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // Test 용도
    @PostMapping("/home")
    public GeneralResponseDto makeReservationHome(@RequestBody MakeReservationHomeRequestDto requestDto) {
        return reservationService.makeReservation(requestDto);
    }
    @PostMapping("/airbnb")
    public GeneralResponseDto makeReservationAirbnb(@RequestBody MakeReservationAirbnbRequestDto requestDto) {
        return reservationService.makeReservation(requestDto);
    }

}
