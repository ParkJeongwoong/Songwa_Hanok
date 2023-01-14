package com.songwa.application.reservation;

import com.songwa.application.common.dto.GeneralResponseDto;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.reservation.dto.MakeReservationAirbnbRequestDto;
import com.songwa.application.reservation.dto.MakeReservationHomeRequestDto;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.application.reservation.service.ReservationService;
import com.songwa.application.room.repository.RoomRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Reservation;
import com.songwa.domain.Room;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll 어노테이션을 non-static으로 사용하기 위한 어노테이션
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    DateRoomRepository dateRoomRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationService reservationService;

    String dateRoom1Id;
    String dateRoom2Id;

    @BeforeAll
    public void setup() {
        String roomName1 = "왼쪽방";
        String roomName2 = "오른쪽방";
        Room room1 = Room.builder().name(roomName1).build();
        Room room2 = Room.builder().name(roomName2).build();
        roomRepository.save(room1);
        roomRepository.save(room2);
        LocalDate now = LocalDate.now();
        DateRoom dateRoom1 = DateRoom.builder().date(now).room(room1).build();
        DateRoom dateRoom2 = DateRoom.builder().date(now).room(room2).build();
        dateRoom1Id = dateRoomRepository.save(dateRoom1).getDateRoomId();
        dateRoom2Id = dateRoomRepository.save(dateRoom2).getDateRoomId();
    }

    @AfterAll
    public void teardown() {
        reservationRepository.deleteAll();
        dateRoomRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @Transactional
    public void test_makeReservation() {
        log.info("makeReservation 테스트 시작");
        // Given
        log.info("makeReservation 테스트 준비");
        String guestName1 = "jeongwoong";
        String guestName2 = "chaewoong";
        String phoneNumber = "010-1234-5678";
        MakeReservationHomeRequestDto requestDto1 = new MakeReservationHomeRequestDto(dateRoom1Id, guestName1, phoneNumber);
        MakeReservationAirbnbRequestDto requestDto2 = new MakeReservationAirbnbRequestDto(dateRoom2Id, guestName2);

        // When
        log.info("makeReservation 테스트 진행");
        long reservationId1 = reservationService.makeReservation(requestDto1).getResultId();
        long reservationId2 = reservationService.makeReservation(requestDto2).getResultId();

        // Then
        log.info("makeReservation 테스트 결과 검증");
        Reservation reservation1 = reservationRepository.findById(reservationId1).orElse(null);
        Reservation reservation2 = reservationRepository.findById(reservationId2).orElse(null);
        DateRoom dateRoom1 = dateRoomRepository.findByDateRoomId(dateRoom1Id);
        DateRoom dateRoom2 = dateRoomRepository.findByDateRoomId(dateRoom2Id);
        assert reservation1 != null;
        assertThat(reservation1.getReservedFrom()).isEqualTo("GuestHome");
        assertThat(reservation1.getDateRoom()).isEqualTo(dateRoom1);
        assertThat(reservation1.getGuest().getName()).isEqualTo(guestName1);
        assert reservation2 != null;
        assertThat(reservation2.getReservedFrom()).isEqualTo("GuestAirbnb");
        assertThat(reservation2.getDateRoom()).isEqualTo(dateRoom2);
        assertThat(reservation2.getGuest().getName()).isEqualTo(guestName2);
    }

    @Test
    public void test_makeReservation_concurrency() throws InterruptedException {
        log.info("makeReservation 동시성 테스트 시작");
        // Given
        log.info("makeReservation 동시성 테스트 준비");
        int numberOfThreads = 4;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        String guestName1 = "jw";
        String guestName2 = "chw";
        String guestName3 = "dad";
        String guestName4 = "mom";
        String phoneNumber = "010-1234-5678";
        MakeReservationHomeRequestDto requestDto1 = new MakeReservationHomeRequestDto(dateRoom1Id, guestName1, phoneNumber);
        MakeReservationHomeRequestDto requestDto2 = new MakeReservationHomeRequestDto(dateRoom1Id, guestName2, phoneNumber);
        MakeReservationHomeRequestDto requestDto3 = new MakeReservationHomeRequestDto(dateRoom1Id, guestName3, phoneNumber);
        MakeReservationHomeRequestDto requestDto4 = new MakeReservationHomeRequestDto(dateRoom1Id, guestName4, phoneNumber);

        // When
        log.info("makeReservation 동시성 테스트 진행");
        service.execute(() -> {
            reservationService.makeReservation(requestDto1);
            latch.countDown();
        });
        service.execute(() -> {
            reservationService.makeReservation(requestDto2);
            latch.countDown();
        });
        service.execute(() -> {
            reservationService.makeReservation(requestDto3);
            latch.countDown();
        });
        service.execute(() -> {
            reservationService.makeReservation(requestDto4);
            latch.countDown();
        });
        latch.await();

        // Then
        log.info("makeReservation 동시성 테스트 결과 검증");
        List<Reservation> reservations = reservationRepository.findAll();
        DateRoom dateRoom = dateRoomRepository.findById(dateRoom1Id).orElseThrow(NoSuchElementException::new);
        log.info("DateRoom 상태 : {} {}", dateRoom.getDateRoomId(), dateRoom.getRoomReservationState());

        Reservation reservation1 = reservations.get(0);
        log.info("Reservation1 결과 : {} {} {}", reservation1.getGuest().getName(), reservation1.getDateRoom().getDateRoomId(), reservation1.getReservationState());
        if (reservations.size()>1) {
            Reservation reservation2 = reservations.get(1);
            log.info("Reservation2 결과 : {} {} {}", reservation2.getGuest().getName(), reservation2.getDateRoom().getDateRoomId(), reservation2.getReservationState());
            Assertions.assertThat(reservation1.getReservationState()).isNotEqualTo(reservation2.getReservationState());
        }
        if (reservations.size()>2) {
            Reservation reservation3 = reservations.get(2);
            log.info("Reservation3 결과 : {} {} {}", reservation3.getGuest().getName(), reservation3.getDateRoom().getDateRoomId(), reservation3.getReservationState());
            Assertions.assertThat(reservation1.getReservationState()).isNotEqualTo(reservation3.getReservationState());
        }
        if (reservations.size()>3) {
            Reservation reservation4 = reservations.get(3);
            log.info("Reservation4 결과 : {} {} {}", reservation4.getGuest().getName(), reservation4.getDateRoom().getDateRoomId(), reservation4.getReservationState());
            Assertions.assertThat(reservation1.getReservationState()).isNotEqualTo(reservation4.getReservationState());
        }
    }

}
