package com.lsp.csv_events.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsp.csv_events.domain.Event;
import com.lsp.csv_events.domain.EventService;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    public void testGetEventsReturnsEmptyList() throws Exception {
        when(eventService.getEvents()).thenReturn(List.of());

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(eventService, times(1)).getEvents();
    }

    @Test
    public void testGetEventsReturnsListOfEvents() throws Exception {
        Event event1 = new Event(1L, "Event A", null, null, null);
        Event event2 = new Event(2L, "Event B", null, null, null);

        when(eventService.getEvents()).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(eventService, times(1)).getEvents();
    }

    @Test
    public void testGetEventByIdReturnsEvent() throws Exception {
        Event event = new Event(1L, "Event A", null, null, null);
        when(eventService.getEvent(1L)).thenReturn(java.util.Optional.of(event));

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Event A"));

        verify(eventService, times(1)).getEvent(1L);
    }

    @Test
    public void testGetEventByIdNotFound() throws Exception {
        when(eventService.getEvent(999L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/events/999"))
                .andExpect(status().isNotFound());

        verify(eventService, times(1)).getEvent(999L);
    }

    @Test
    public void testSaveEventsSuccessfully() throws Exception {
        Event event1 = new Event(1L, "Event A", null, null, null);
        Event event2 = new Event(2L, "Event B", null, null, null);
        List<Event> events = List.of(event1, event2);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(events);

        mockMvc.perform(post("/events")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());

        verify(eventService, times(1)).saveEvents(events);
    }

    @Test
    public void testSaveEmptyEventsList() throws Exception {
        List<Event> events = List.of();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(events);

        mockMvc.perform(post("/events")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());

        verify(eventService, times(1)).saveEvents(events);
    }
}