package com.songwa.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false)
    private String name;
}
