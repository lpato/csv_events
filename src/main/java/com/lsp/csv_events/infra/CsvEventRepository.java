package com.lsp.csv_events.infra;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lsp.csv_events.domain.Event;
import com.lsp.csv_events.domain.EventRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CsvEventRepository implements EventRepository {

    private final CsvEventReader reader;

    private final CsvEventWriter writer;

    @Override
    public List<Event> findAll() {
        return reader.read();
    }

    @Override
    public Optional<Event> findById(Long id) {

        List<Event> events = reader.read();
        return events.stream()
                .filter(u -> Objects.equals(u.id(), id))
                .findFirst();
    }

    @Override
    public void saveAll(List<Event> events) {
        writer.write(events);
    }

}
