package com.songwa.adapter.controller;

import com.songwa.application.dateroom.dto.*;
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

    // TEST 용도
    @PostMapping("make-room")
    public long makeRoom(@RequestBody makeHanokRoomDto requestDto) {
        return dateRoomService.makeRoom(requestDto);
    }
    @PostMapping("make-dateroom")
    public String makeDateRoom(@RequestBody makeDateRoomDto requestDto) throws Exception {
        return dateRoomService.makeDateRoom(requestDto);
    }
    @PostMapping("make-reservation-home")
    public void makeReservationHome(@RequestBody makeReservationHomeRequestDto requestDto) {
        dateRoomService.makeReservation(requestDto);
    }
    @PostMapping("make-reservation-airbnb")
    public void makeReservationAirbnb(@RequestBody makeReservationAirbnbRequestDto requestDto) {
        dateRoomService.makeReservation(requestDto);
    }

}
