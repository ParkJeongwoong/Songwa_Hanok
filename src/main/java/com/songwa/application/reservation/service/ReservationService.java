package com.songwa.application.reservation.service;

import com.songwa.application.common.dto.GeneralResponseDto;
import com.songwa.application.dateroom.etc.exception.RoomReservationException;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.guest.repository.GuestRepository;
import com.songwa.application.reservation.dto.MakeReservationRequestDto;
import com.songwa.application.reservation.etc.eventLoop.ReservationHandler;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Guest;
import com.songwa.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final DateRoomRepository dateRoomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationHandler reservationHandler;

    @Transactional
    public GeneralResponseDto makeReservation(MakeReservationRequestDto requestDto) {
        try {
            log.info("예약 시작 : {}", requestDto.getGuestName());
            DateRoom dateRoom = dateRoomRepository.findByDateRoomId(requestDto.getDateRoomId());
            dateRoom.setStateBooking();
            dateRoomRepository.save(dateRoom);

            Guest guest = requestDto.makeGuest();
            Reservation reservation = Reservation.builder()
                    .dateRoom(dateRoom)
                    .guest(guest)
                    .build();
            guestRepository.save(guest);

            long reservationId = reservationRepository.save(reservation).getId();
            reservationHandler.add(reservationId);

            log.info("{} 고객님의 예약이 완료되었습니다.", requestDto.getGuestName());
            return GeneralResponseDto.builder()
                    .successYN("Y")
                    .resultId(reservationId)
                    .message("예약에 성공했습니다.")
                    .build();
        } catch (RoomReservationException e) {
            log.error("예약된 날짜 에러 - {}", requestDto.getGuestName(), e);
            return GeneralResponseDto.builder()
                    .successYN("N")
                    .message("이미 예약된 날짜입니다.")
                    .build();
        } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException e) {
            log.error("예약된 날짜 에러(낙관적 락) - {}", requestDto.getGuestName(), e);
            return GeneralResponseDto.builder()
                    .successYN("N")
                    .message("이미 예약된 날짜입니다.")
                    .build();
        }
    }

}
