package com.songwa;

import com.songwa.application.dateroom.service.DateRoomService;
import com.songwa.application.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {

    private final RoomService roomService;
    private final DateRoomService dateRoomService;

    @PostConstruct
    void setKST() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @PostConstruct
    public void init() {
        roomService.makeRoom("A", 250000, 300000, 330000, 220000);
        roomService.makeRoom("B", 250000, 300000, 330000, 220000);
        dateRoomService.make3MonthsDateRoom();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
