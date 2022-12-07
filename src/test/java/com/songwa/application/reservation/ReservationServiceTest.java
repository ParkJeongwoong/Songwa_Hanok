package com.songwa.application.reservation;

import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.reservation.dto.MakeReservationAirbnbRequestDto;
import com.songwa.application.reservation.dto.MakeReservationHomeRequestDto;
import com.songwa.application.reservation.repository.ReservationRepository;
import com.songwa.application.reservation.service.ReservationService;
import com.songwa.application.room.repository.RoomRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Reservation;
import com.songwa.domain.Room;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        // Given
        String guestName1 = "jeongwoong";
        String guestName2 = "chaewoong";
        String phoneNumber = "010-1234-5678";
        MakeReservationHomeRequestDto requestDto1 = new MakeReservationHomeRequestDto(dateRoom1Id, guestName1, phoneNumber);
        MakeReservationAirbnbRequestDto requestDto2 = new MakeReservationAirbnbRequestDto(dateRoom2Id, guestName2);

        // When
        long reservationId1 = reservationService.makeReservation(requestDto1).getResultId();
        long reservationId2 = reservationService.makeReservation(requestDto2).getResultId();

        // Then
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
}
