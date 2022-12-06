package com.songwa.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class HanokRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false)
    private final String name;

    @Builder
    public HanokRoom(String name) {
        this.name = name;
    }
}
