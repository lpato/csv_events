package com.lsp.csv_events.infra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lsp.csv_events.domain.Event;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CsvEventReader {

    private final CsvEventProperties csvEventProperties;

    List<Event> read() {

        List<Event> events = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(csvEventProperties.getFile()))) {
            events = reader.lines()
                    .filter(line -> !line.isBlank())
                    .map(line -> line.split(","))
                    .map(this::toEvent)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        // unreachable if try returns; kept as fallback
        return events;
    }

    private Event toEvent(String[] split) {

        Long id = Long.parseLong(split[0]);
        LocalDateTime start = LocalDateTime.parse(split[3]);
        LocalDateTime end = LocalDateTime.parse(split[4]);

        return new Event(id, split[1], split[2], start, end);

    }

}
