package com.lsp.csv_events.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    public List<Event> getEvents() {

        return eventRepository.findAll();
    }

    public void saveEvents(List<Event> events) {

        eventRepository.saveAll(events);
    }

    public Optional<Event> getEvent(Long id) {

        return eventRepository.findById(id);
    }

}
