package com.ipass.api.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ipass.api.event.Event;
import com.ipass.api.event.EventRepository;

@SpringBootTest
public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventRepository eventRepository;

    @Test
    void testCreateEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setStartDateTime(LocalDateTime.now().minusHours(1));
        event.setEndDateTime(LocalDateTime.now().plusHours(1));

        when(eventRepository.save(event)).thenReturn(event);

        ResponseEntity<String> response = eventController.createEvent(event);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setStartDateTime(LocalDateTime.now().minusHours(1));
        event.setEndDateTime(LocalDateTime.now().plusHours(1));

        when(eventRepository.existsById(1L)).thenReturn(true);
        when(eventRepository.save(event)).thenReturn(event);

        ResponseEntity<String> response = eventController.updateEvent(1L, event);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // @Test
    // void testGetAllEvents() {
    //     Event event = new Event();
    //     event.setId(1L);
    //     event.setStartDateTime(LocalDateTime.now().minusHours(1));
    //     event.setEndDateTime(LocalDateTime.now().plusHours(1));
    //     List<Event> events = Arrays.asList(event);
    //     Pageable pageable = PageRequest.of(0, 10);
    //     Page<Event> eventPage = new PageImpl<>(events, pageable, events.size());

    //     when(eventRepository.findAll(pageable)).thenReturn(eventPage);

    //     ResponseEntity<Page<Event>> response = eventController.getAllEvents(0, 10, "id,asc");
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(1, response.getBody().getContent().size());
    // }

    @Test
    void testGetEventById() {
        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        ResponseEntity<Event> response = eventController.getEventById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody());
    }

    @Test
    void testDeleteEvent() {
        when(eventRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = eventController.deleteEvent(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}