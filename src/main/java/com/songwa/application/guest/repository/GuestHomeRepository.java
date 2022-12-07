package com.songwa.application.guest.repository;

import com.songwa.domain.GuestHome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestHomeRepository extends JpaRepository<GuestHome, Long> {
}
