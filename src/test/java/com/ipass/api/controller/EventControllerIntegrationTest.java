package com.ipass.api.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ipass.api.event.Event;
import com.ipass.api.event.EventRepository;

@WebMvcTest(EventController.class)
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;
    
    // @Test
    // void testCreateEvent() throws Exception {
    //     Event event = new Event();
    //     event.setId(1L);
    //     event.setStartDateTime(LocalDateTime.now().minusHours(1));
    //     event.setEndDateTime(LocalDateTime.now().plusHours(1));

    //     given(eventRepository.save(event)).willReturn(event);

    //     mockMvc.perform(MockMvcRequestBuilders.post("/events")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("{ \"id\": 1, \"startDateTime\": \"" + event.getStartDateTime().format(DateTimeFormatter.ISO_DATE_TIME) +
    //                     "\", \"endDateTime\": \"" + event.getEndDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + "\" }"))
    //             .andExpect(MockMvcResultMatchers.status().isCreated());
    // }

    // @Test
    // void testGetAllEvents() throws Exception {
    //     Event event = new Event();
    //     event.setId(1L);
    //     event.setStartDateTime(LocalDateTime.now().minusHours(1));
    //     event.setEndDateTime(LocalDateTime.now().plusHours(1));

    //     given(eventRepository.findAll()).willReturn(Arrays.asList(event));

    //     mockMvc.perform(MockMvcRequestBuilders.get("/events"))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    // }

    @Test
    void testGetEventById() throws Exception {
        Event event = new Event();
        event.setId(1L);

        given(eventRepository.findById(1L)).willReturn(Optional.of(event));

        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}