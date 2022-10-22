package com.songwa.adapter.controller;

import com.songwa.application.dateroom.dto.DateRoomInfoDto;
import com.songwa.application.dateroom.dto.makeReservationRequestDto;
import com.songwa.application.dateroom.service.DateRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("dateroom")
public class DateRoomController {

    private DateRoomService dateRoomService;

    @GetMapping("show-all")
    public List<DateRoomInfoDto> showAllDateRooms() {
        return dateRoomService.showAllDateRooms();
    }

    @PostMapping("start-reservation")
    public void startReservation(@RequestBody makeReservationRequestDto requestDto) {
        dateRoomService.makeReservation(requestDto);
    }

}
