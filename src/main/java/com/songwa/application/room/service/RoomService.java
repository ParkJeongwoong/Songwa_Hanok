package com.songwa.application.room.service;

import com.songwa.application.room.dto.RoomInfoDto;
import com.songwa.application.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomInfoDto> showAllRooms() {
        return roomRepository.findAll().stream().map(RoomInfoDto::new).collect(Collectors.toList());
    }
}
