package com.lsp.csv_events.domain;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    List<Event> findAll();

    Optional<Event> findById(Long id);

    void saveAll(List<Event> events);

}
