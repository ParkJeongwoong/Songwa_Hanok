package com.songwa.domain;

import com.songwa.application.dateroom.etc.exception.reservationException;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
public class DateRoom {
    @Id
    private final String dateRoomId;

    @Column(nullable = false)
    private final LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private final Room room;

    @Column(nullable = false)
    private long reservationState; // 0 : 미예약, -1 : 예약 완료

    @Builder
    DateRoom(LocalDate date, Room room) {
        this.date = date;
        this.room = room;
        this.reservationState = 0;
        this.dateRoomId = date.toString() + "||" + room.getId();
    }

    public void makeReservation() throws reservationException {
        if (this.reservationState == 0) {
            this.reservationState = -1;
        } else {
            throw new reservationException("이미 예약된 날짜입니다.");
        }
    }
}
