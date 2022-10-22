package com.songwa.application.dateroom.service;

import com.songwa.application.dateroom.dto.DateRoomInfoDto;
import com.songwa.application.dateroom.dto.makeReservationRequestDto;
import com.songwa.application.dateroom.etc.exception.reservationException;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.domain.DateRoom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateRoomService extends Thread {
    private DateRoomRepository dateRoomRepository;

    public List<DateRoomInfoDto> showAllDateRooms() {
        return dateRoomRepository.findAll().stream().map(DateRoomInfoDto::new).collect(Collectors.toList());
    }

    @Transactional
    public synchronized void makeReservation(makeReservationRequestDto requestDto) {
        DateRoom dateRoom = dateRoomRepository.findById(requestDto.getDateRoomId()).orElseThrow(NullPointerException::new);
        dateRoom.makeReservation();
    }
}
