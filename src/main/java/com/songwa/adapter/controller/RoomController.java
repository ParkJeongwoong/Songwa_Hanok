package com.songwa.adapter.controller;

import com.songwa.application.room.dto.RoomInfoDto;
import com.songwa.application.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("show-all")
    public List<RoomInfoDto> showAllRooms() {
        return roomService.showAllRooms();
    }

}
