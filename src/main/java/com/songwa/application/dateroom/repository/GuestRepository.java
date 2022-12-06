package com.songwa.application.dateroom.repository;

import com.songwa.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

//public interface GuestRepository<T extends Guest> extends JpaRepository<T, Long> {
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
