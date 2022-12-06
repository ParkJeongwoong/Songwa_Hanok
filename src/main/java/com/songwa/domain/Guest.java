package com.songwa.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

//@SuperBuilder
@Getter
//@MappedSuperclass
@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(length = 20, nullable = false)
    protected String name;

//    @Builder
    public Guest(String name) {
        this.name = name;
    }
}