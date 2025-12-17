package com.lsp.csv_events.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(1L, "Event A", null, null, null);
    }

    @Test
    void testGetEvents() {
        List<Event> events = List.of(event);
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getEvents();

        assertEquals(1, result.size());
        assertEquals(event, result.get(0));
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testSaveEvents() {
        List<Event> events = List.of(event);

        eventService.saveEvents(events);

        verify(eventRepository, times(1)).saveAll(events);
    }

    @Test
    void testGetEventFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEvent(1L);

        assertTrue(result.isPresent());
        assertEquals(event, result.get());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEventNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEvent(1L);

        assertTrue(result.isEmpty());
        verify(eventRepository, times(1)).findById(1L);
    }
}