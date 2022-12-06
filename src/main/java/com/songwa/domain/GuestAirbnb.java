package com.songwa.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

//@SuperBuilder
@Getter
@Entity
public class GuestAirbnb extends Guest {

    @Builder
    public GuestAirbnb(String name) {
        super(name);
    }

}
