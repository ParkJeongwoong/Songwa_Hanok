package com.songwa.application.dateroom;

import com.songwa.application.dateroom.dto.DateRoomInfoDto;
import com.songwa.application.dateroom.dto.MakeDateRoomDto;
import com.songwa.application.dateroom.repository.DateRoomRepository;
import com.songwa.application.dateroom.service.DateRoomService;
import com.songwa.application.room.repository.RoomRepository;
import com.songwa.domain.DateRoom;
import com.songwa.domain.Room;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll 어노테이션을 non-static으로 사용하기 위한 어노테이션
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DateRoomServiceTest {

    @Autowired
    DateRoomRepository dateRoomRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    DateRoomService dateRoomService;

    long roomId1;
    long roomId2;

    @BeforeAll
    public void setup() {
        String roomName1 = "왼쪽방";
        String roomName2 = "오른쪽방";
        Room room1 = Room.builder().name(roomName1).build();
        Room room2 = Room.builder().name(roomName2).build();
        roomId1 = roomRepository.save(room1).getId();
        roomId2 = roomRepository.save(room2).getId();
    }

    @AfterEach
    public void cleanup() { dateRoomRepository.deleteAll(); roomRepository.deleteAll(); }

    @AfterAll
    public void teardown() {
        dateRoomRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @Transactional // DateRoom - Room의 관계가 ManyToOne, FetchType.LAZY이기 때문에 could not initialize proxy - no Session 에러 발생
    public void test_makeDateRoom() throws Exception {
        // Given
        LocalDate now = LocalDate.now();
        MakeDateRoomDto requestDto = MakeDateRoomDto.builder().date(now).roomId(roomId2).build();

        // When
        String dateRoomId = dateRoomService.makeDateRoom(requestDto);

        // Then
        DateRoom dateRoom = dateRoomRepository.findByDateRoomId(dateRoomId);
        Room room = roomRepository.findById(roomId2).orElseThrow(()->new Exception("존재하지 않는 방입니다."));
        assertThat(dateRoom.getDate()).isEqualTo(now);
        assertThat(dateRoom.getRoom()).isEqualTo(room);
        assertThat(dateRoom.getRoomReservationState()).isEqualTo(0);
    }

    @Test
    @Transactional // DateRoom - Room의 관계가 ManyToOne, FetchType.LAZY이기 때문에 could not initialize proxy - no Session 에러 발생
    public void test_showAllDateRooms() throws Exception {
        // Given
        LocalDate now = LocalDate.now();
        Room room1 = roomRepository.findById(roomId1).orElseThrow(()->new Exception("존재하지 않는 방입니다."));
        Room room2 = roomRepository.findById(roomId2).orElseThrow(()->new Exception("존재하지 않는 방입니다."));
        DateRoom dateRoom1 = DateRoom.builder()
                .date(now)
                .room(room1)
                .build();
        DateRoom dateRoom2 = DateRoom.builder()
                .date(now)
                .room(room2)
                .build();

        dateRoomRepository.save(dateRoom1);
        dateRoomRepository.save(dateRoom2);

        // When
        List<DateRoomInfoDto> dateRoomInfoDtoList = dateRoomService.showAllDateRooms();

        // Then
        DateRoomInfoDto dateRoomInfo1 = dateRoomInfoDtoList.get(0);
        DateRoomInfoDto dateRoomInfo2 = dateRoomInfoDtoList.get(1);
        assertThat(dateRoomInfo1.getDate()).isEqualTo(now);
        assertThat(dateRoomInfo1.getRoomId()).isEqualTo(roomId1);
        assertThat(dateRoomInfo1.getReservationState()).isEqualTo(0);
        assertThat(dateRoomInfo2.getDate()).isEqualTo(now);
        assertThat(dateRoomInfo2.getRoomId()).isEqualTo(roomId2);
        assertThat(dateRoomInfo2.getReservationState()).isEqualTo(0);
    }

}
