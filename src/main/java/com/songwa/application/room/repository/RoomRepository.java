package com.songwa.application.room.repository;

import com.songwa.domain.HanokRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<HanokRoom, Long> {
}
