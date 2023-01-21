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

    @PostConstruct
    void setKST() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
