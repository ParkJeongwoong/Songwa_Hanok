package com.songwa.application.dateroom.service;

import com.songwa.application.dateroom.dto.*;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.room.repository.RoomRepository;
import com.songwa.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DateRoomService extends Thread {

    private final DateRoomRepository dateRoomRepository;
    private final RoomRepository roomRepository;

    public List<DateRoomInfoDto> showAllDateRooms() {
        return dateRoomRepository.findAll().stream().map(DateRoomInfoDto::new).collect(Collectors.toList());
    }

    @Transactional
    public String makeDateRoom(MakeDateRoomDto requestDto) throws Exception {
        long roomId = requestDto.getRoomId();
        Room room = roomRepository.findById(roomId).orElseThrow(()->new Exception("존재하지 않는 방입니다."));
        DateRoom dateRoom = DateRoom.builder()
                                .date(requestDto.getDate())
                                .room(room)
                                .build();
        return dateRoomRepository.save(dateRoom).getDateRoomId();
    }

}
