package com.lsp.csv_events.api;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lsp.csv_events.domain.Event;
import com.lsp.csv_events.domain.EventService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
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

        eventService.saveEvents(events);
    }

}
