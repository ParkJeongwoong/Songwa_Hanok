package com.songwa.application.room.service;

import com.songwa.application.room.dto.RoomInfoDto;
import com.songwa.application.room.dto.MakeRoomDto;
import com.songwa.application.room.repository.RoomRepository;
import com.songwa.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomInfoDto> showAllRooms() {
        return roomRepository.findAll().stream().map(RoomInfoDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void makeRoom(String name, long price, long priceWeekend, long priceHoliday, long priceSpecial) {
        Room room = Room.builder().name(name).price(price).priceWeekend(priceWeekend).priceHoliday(priceHoliday).priceSpecial(priceSpecial).build();
        roomRepository.save(room);
    }

    @Transactional
    public long makeRoom(MakeRoomDto requestDto) {
        Room room = Room.builder().name(requestDto.getName()).price(requestDto.getPrice()).build();
        return roomRepository.save(room).getId();
    }

}
