package com.ipass.api.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipass.api.event.Event;
import com.ipass.api.event.EventRepository;

@RestController
@RequestMapping("/events")
public class EventController {  

    @Autowired
    private EventRepository eventRepository;
    
    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]));
        Page<Event> events = eventRepository.findAll(pageable);
        if (events == null || events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return new ResponseEntity<>(event.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/duration/{id}")
    public ResponseEntity<String> getEventDuration(@PathVariable Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Duration duration = Duration.between(event.getStartDateTime(), event.getEndDateTime());

            // Convertendo a duração em horas e minutos
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;

            return new ResponseEntity<>("Duração do evento: " + hours + " horas e " + minutes + " minutos", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Evento não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/within-period")
    public ResponseEntity<List<Event>> getEventsWithinPeriod(
        @RequestParam("start") String start,
        @RequestParam("end") String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        List<Event> events = eventRepository.findEventsWithinPeriod(startDateTime, endDateTime);
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }
    
    @PostMapping
    public ResponseEntity<String> createEvent(@Validated @RequestBody Event event) {
        if (event.getEndDateTime().isBefore(event.getStartDateTime())) {
            return new ResponseEntity<>("endDateTime must be after startDateTime", HttpStatus.BAD_REQUEST);
        }
        Event savedEvent = eventRepository.save(event);
        return new ResponseEntity<>(savedEvent.toString(), HttpStatus.CREATED); // Use a representação adequada de Event
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @Validated @RequestBody Event event) {
        if (event.getEndDateTime().isBefore(event.getStartDateTime())) {
            return new ResponseEntity<>("endDateTime must be after startDateTime", HttpStatus.BAD_REQUEST);
        }
        if (!eventRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        event.setId(id);
        Event updatedEvent = eventRepository.save(event);
        return new ResponseEntity<>(updatedEvent.toString(), HttpStatus.OK); // Use a representação adequada de Event
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (!eventRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}