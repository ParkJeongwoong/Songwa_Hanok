package com.songwa.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

//@SuperBuilder
@Getter
@Entity
public class GuestHome extends Guest {

    @Column(length = 20, nullable = false)
    private final String phoneNumber;

    @Builder
    public GuestHome(String name, String phoneNumber) {
        super(name);
        this.phoneNumber = phoneNumber;
    }

}
