package com.songwa.application.dateroom.service;

import com.songwa.application.dateroom.dto.DateRoomInfoDto;
import com.songwa.application.dateroom.dto.makeDateRoomDto;
import com.songwa.application.dateroom.dto.makeReservationRequestDto;
import com.songwa.application.dateroom.dto.makeHanokRoomDto;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.dateroom.repository.GuestRepository;
import com.songwa.application.dateroom.repository.ReservationRepository;
import com.songwa.application.dateroom.repository.HanokRoomRepository;
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
    private final HanokRoomRepository hanokRoomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public List<DateRoomInfoDto> showAllDateRooms() {
        return dateRoomRepository.findAll().stream().map(DateRoomInfoDto::new).collect(Collectors.toList());
    }

    @Transactional
    public synchronized void makeReservation(makeReservationRequestDto requestDto) {
        DateRoom dateRoom = dateRoomRepository.findByDateRoomId(requestDto.getDateRoomId());
        Guest guest = requestDto.makeGuest();
        Reservation reservation = Reservation.builder()
                                        .dateRoom(dateRoom)
                                        .guest(guest)
                                        .build();
        guestRepository.save(guest);
        reservationRepository.save(reservation);
        dateRoom.setToReservation();
    }

    @Transactional
    public long makeRoom(makeHanokRoomDto requestDto) {
        HanokRoom hanokRoom = HanokRoom.builder().name(requestDto.getName()).build();
        return hanokRoomRepository.save(hanokRoom).getId();
    }

    @Transactional
    public String makeDateRoom(makeDateRoomDto requestDto) throws Exception {
        long roomId = requestDto.getRoomId();
        HanokRoom hanokRoom = hanokRoomRepository.findById(roomId).orElseThrow(()->new Exception("존재하지 않는 방입니다."));
        DateRoom dateRoom = DateRoom.builder()
                                .date(requestDto.getDate())
                                .hanokRoom(hanokRoom)
                                .build();
        return dateRoomRepository.save(dateRoom).getDateRoomId();
    }
}
