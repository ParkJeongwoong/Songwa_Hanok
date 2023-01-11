package com.songwa.application.reservation.service;

import com.songwa.application.common.dto.GeneralResponseDto;
import com.songwa.application.dateroom.etc.exception.RoomReservationException;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.guest.repository.GuestRepository;
import com.songwa.application.reservation.dto.MakeReservationRequestDto;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Guest;
import com.songwa.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {
    private final DateRoomRepository dateRoomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public synchronized GeneralResponseDto makeReservation(MakeReservationRequestDto requestDto) {
        // Todo : 동시성 테스트
        try {
            log.info("예약 시작 : {}", requestDto.getGuestName());
            DateRoom dateRoom = dateRoomRepository.findByDateRoomId(requestDto.getDateRoomId());
            dateRoom.setStateBooking();

            Guest guest = requestDto.makeGuest();
            Reservation reservation = Reservation.builder()
                    .dateRoom(dateRoom)
                    .guest(guest)
                    .build();
            // Todo : 스케쥴러를 이용한 주기적 확인 (취소 작업)

            guestRepository.save(guest);
            long reservationId = reservationRepository.save(reservation).getId();

            log.info("{} 고객님의 예약이 완료되었습니다.", requestDto.getGuestName());
            return GeneralResponseDto.builder()
                    .successYN("Y")
                    .resultId(reservationId)
                    .message("예약에 성공했습니다.")
                    .build();
        } catch (RoomReservationException e) {
            log.error("예약된 날짜 에러", e);
            return GeneralResponseDto.builder()
                    .successYN("N")
                    .message("이미 예약된 날짜입니다.")
                    .build();
        }
    }
}
