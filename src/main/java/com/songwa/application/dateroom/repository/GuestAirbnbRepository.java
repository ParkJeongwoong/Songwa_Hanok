package com.songwa.application.dateroom.repository;

import com.songwa.domain.GuestAirbnb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestAirbnbRepository extends JpaRepository<GuestAirbnb, Long> {
}
