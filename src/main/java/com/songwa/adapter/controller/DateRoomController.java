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

    private final DateRoomService dateRoomService;

    // Setting 용도
    @PostMapping("/")
    public String makeDateRoom(@RequestBody MakeDateRoomDto requestDto) throws Exception {
        return dateRoomService.makeDateRoom(requestDto);
    }

    @GetMapping("dateRoomList")
    public List<DateRoomInfoDto> showAllDateRooms() {
        return dateRoomService.showAllDateRooms();
    }
}
