package com.songwa.application.reservation.etc.scheduler;

import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.reservation.dto.ReservationSchedulerDto;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReservationScheduler {

    private static final long EXPIRATION_TIME = 30;

    private Queue<ReservationSchedulerDto> notPayedReservationList;
    private final ReservationRepository reservationRepository;
    private final DateRoomRepository dateRoomRepository;

    @PostConstruct
    private void init() {
        this.notPayedReservationList = new LinkedList<>();
    }

    public void add(long reservationId) {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
        ReservationSchedulerDto schedulerDto = ReservationSchedulerDto.builder()
                .reservationId(reservationId).expirationTime(expirationTime).build();
        if (!this.notPayedReservationList.offer(schedulerDto)) {
            log.error("미결제 예약 대기번호 추가 실패");
        }
        log.info("예약번호 : {} / 예약기한 : {}", reservationId, expirationTime);
    }

    @Scheduled(cron = "0/10 * * * * *")
    private void checkExpiredReservation() {
        log.info("결제 대기 예약 수 : {}", notPayedReservationList.size());
        while (this.notPayedReservationList.peek() != null) {
            ReservationSchedulerDto reservationDto = this.notPayedReservationList.peek();
            if (expirationCheck(reservationDto.getExpirationTime())) {
                log.info("미결제 예약 취소 - 예약번호 : {}", reservationDto.getReservationId());
                expireReservation(this.notPayedReservationList.poll().getReservationId());
            } else {
                break;
            }
        }
    }

    private boolean expirationCheck(LocalDateTime expirationTime) {
        return expirationTime.isBefore(LocalDateTime.now());
    }

    private void expireReservation(long reservationId) {
        try {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NoSuchElementException::new);
            DateRoom dateRoom = reservation.getDateRoom();
            reservation.setStateTimeOut();
            dateRoom.resetState();
            reservationRepository.save(reservation);
            dateRoomRepository.save(dateRoom);
        } catch (NoSuchElementException e) {
            log.error("Reservation 조회 에러", e);
        }
    }

}
