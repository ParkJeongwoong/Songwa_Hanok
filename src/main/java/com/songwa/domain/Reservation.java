package com.songwa.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Reservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "dateRoom_id", nullable = false)
    private final DateRoom dateRoom;

    @OneToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private final Guest guest;

    private final String reservedFrom;

    @Builder
    public Reservation(DateRoom dateRoom, Guest guest, String reservedFrom) {
        this.dateRoom = dateRoom;
        this.guest = guest;
        this.reservedFrom = reservedFrom;
    }
}
