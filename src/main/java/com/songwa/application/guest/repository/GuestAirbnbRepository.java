package com.songwa.application.guest.repository;

import com.songwa.domain.GuestAirbnb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestAirbnbRepository extends JpaRepository<GuestAirbnb, Long> {
}
