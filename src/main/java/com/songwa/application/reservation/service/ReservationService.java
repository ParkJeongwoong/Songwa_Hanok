package com.songwa.application.reservation.service;

import com.songwa.application.common.dto.GeneralResponseDto;
import com.songwa.application.dateroom.etc.exception.reservationException;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.guest.repository.GuestRepository;
import com.songwa.application.reservation.dto.MakeReservationRequestDto;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Guest;
import com.songwa.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final DateRoomRepository dateRoomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public synchronized GeneralResponseDto makeReservation(MakeReservationRequestDto requestDto) {
        DateRoom dateRoom = dateRoomRepository.findByDateRoomId(requestDto.getDateRoomId());
        Guest guest = requestDto.makeGuest();
        Reservation reservation = Reservation.builder()
                .dateRoom(dateRoom)
                .guest(guest)
                .build();
        try {
            dateRoom.setToReservation();
            guestRepository.save(guest);
            long reservationId = reservationRepository.save(reservation).getId();

            return GeneralResponseDto.builder()
                    .successYN("Y")
                    .resultId(reservationId)
                    .message("예약에 성공했습니다.")
                    .build();
        } catch (reservationException e) {
            return GeneralResponseDto.builder()
                    .successYN("N")
                    .message("이미 예약된 날짜입니다.")
                    .build();
        }
    }
}
