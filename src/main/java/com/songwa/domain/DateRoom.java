package com.songwa.domain;

import com.songwa.application.dateroom.etc.exception.reservationException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class DateRoom {
    @Id
    private String dateRoomId;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private long real_price;

    @Column(nullable = false)
    private int priceType; // 0 : 평일, 1 : 주말, 2 : 연휴, 3 : 특별가

    @Column(nullable = false)
    private long reservationState; // 0 : 미예약, -1 : 예약 완료

    @Builder
    DateRoom(LocalDate date, Room room) {
        this.date = date;
        this.room = room;
        this.reservationState = 0;
        this.dateRoomId = date.toString() + "&&" + room.getId();
        setPriceType();
        setRealPrice();
    }

    public void setToReservation() throws reservationException {
        if (this.reservationState == 0) {
            this.reservationState = -1;
        } else {
            throw new reservationException("이미 예약된 날짜입니다.");
        }
    }

    public long changePriceType(int priceType) {
        this.priceType = priceType;
        setRealPrice();
        return this.priceType;
    }

    private void setPriceType() {
        DayOfWeek dayOfWeek = this.date.getDayOfWeek();
        switch (dayOfWeek) {
            case FRIDAY:
            case SATURDAY:
                this.priceType = 1;
                break;
            default:
                this.priceType = 0;
                break;
        }
    }

    private void setRealPrice() {
        switch (this.priceType) {
            case 0:
                this.real_price = this.room.getPrice();
                break;
            case 1:
                this.real_price = this.room.getPriceWeekend();
                break;
            case 2:
                this.real_price = this.room.getPriceHoliday();
                break;
            case 3:
                this.real_price = this.room.getPriceSpecial();
                break;
        }
    }
}
