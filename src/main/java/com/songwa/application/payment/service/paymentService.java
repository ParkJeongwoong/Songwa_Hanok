package com.songwa.application.payment.service;

import com.songwa.application.common.dto.GeneralResponseDto;
import com.songwa.application.dateroom.etc.exception.RoomReservationException;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.guest.repository.GuestRepository;
import com.songwa.application.payment.dto.PaymentRequestDto;
import com.songwa.application.reservation.etc.exception.ReservationException;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Guest;
import com.songwa.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class paymentService {

    private final ReservationRepository reservationRepository;
    private final DateRoomRepository dateRoomRepository;
    private final GuestRepository guestRepository;

    public GeneralResponseDto pay(PaymentRequestDto requestDto) {
        try {
            Reservation reservation = reservationRepository.findById(requestDto.getReservationId()).orElseThrow(NoSuchElementException::new);
            DateRoom dateRoom = dateRoomRepository.findById(requestDto.getDateRoomId()).orElseThrow(NoSuchElementException::new);
            Guest guest = guestRepository.findById(requestDto.getGuestId()).orElseThrow(NoSuchElementException::new);

            // 결제 - 아임포트 / PG사

            // 결제 완료
            log.info("결제 완료");
            dateRoom.setStateBooked();
            reservation.setStatePayed();

            // Todo : 결제 완료 문자 전송

            return GeneralResponseDto.builder()
                    .successYN("Y")
                    .message("예약이 확정되었습니다.")
                    .build();
        }
        catch (RoomReservationException dateRoomException) {
            log.error("방날짜 예약 완료 오류", dateRoomException);
            return GeneralResponseDto.builder()
                    .successYN("N")
                    .message("예약 완료 작업 중 오류가 발생했습니다.")
                    .build();
        }
        catch (ReservationException reservationException) {
            log.error("예약 완료 오류", reservationException);
            return GeneralResponseDto.builder()
                    .successYN("N")
                    .message("예약 완료 작업 중 오류가 발생했습니다.")
                    .build();
        }

    }
}
