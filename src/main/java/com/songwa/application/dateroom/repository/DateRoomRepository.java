package com.songwa.application.dateroom.repository;

import com.songwa.domain.DateRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;

public interface DateRoomRepository extends JpaRepository<DateRoom, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    DateRoom findByDateRoomId(String dateRoomId);
    List<DateRoom> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
