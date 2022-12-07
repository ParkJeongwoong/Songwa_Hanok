package com.songwa.adapter.controller;

import com.songwa.application.room.dto.RoomInfoDto;
import com.songwa.application.room.dto.MakeRoomDto;
import com.songwa.application.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("room")
public class RoomController {

    private final RoomService roomService;

    // Setting 용도
    @PostMapping("/")
    public long makeRoom(@RequestBody MakeRoomDto requestDto) {
        return roomService.makeRoom(requestDto);
    }

    @GetMapping("roomList")
    public List<RoomInfoDto> showAllRooms() {
        return roomService.showAllRooms();
    }
}
