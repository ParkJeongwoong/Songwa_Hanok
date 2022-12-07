package com.songwa.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "dateRoom_id", nullable = false)
    private DateRoom dateRoom;

    @OneToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    private String reservedFrom;

    @Builder
    public Reservation(DateRoom dateRoom, Guest guest) {
        this.dateRoom = dateRoom;
        this.guest = guest;
        this.reservedFrom = guest.getClass().getSimpleName();
    }
}
