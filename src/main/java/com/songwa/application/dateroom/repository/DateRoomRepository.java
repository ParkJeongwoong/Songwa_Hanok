package com.songwa.application.dateroom.repository;

import com.songwa.domain.DateRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface DateRoomRepository extends JpaRepository<DateRoom, String> {
//    @Lock(LockModeType.OPTIMISTIC)
//    List<DateRoom> findAllOrderByDateRoomId();

    DateRoom findByDateRoomId(String dateRoomId);
}
