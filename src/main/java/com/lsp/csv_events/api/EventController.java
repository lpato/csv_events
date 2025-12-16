package com.lsp.csv_events.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lsp.csv_events.domain.Event;
import com.lsp.csv_events.domain.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> getEvents() {

        return eventService.getEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {

        return eventService.getEvent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public void saveEvents(@RequestBody List<Event> events) {
        log.info("Received request to save {} events", events.size());

        eventService.saveEvents(events);
        log.info("Successfully saved {} events", events.size());

    }

}
