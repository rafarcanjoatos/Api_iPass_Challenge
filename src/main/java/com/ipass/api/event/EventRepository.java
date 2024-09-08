package com.ipass.api.event;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Método para buscar eventos dentro de um período específico
    @Query("SELECT e FROM Event e WHERE e.startDateTime >= :start AND e.endDateTime <= :end")
    List<Event> findEventsWithinPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
