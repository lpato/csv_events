package com.lsp.csv_events.infra;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.stereotype.Component;

import com.lsp.csv_events.domain.Event;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CsvEventWriter {

    private final CsvEventProperties csvEventProperties;

    void write(List<Event> events) {

        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(csvEventProperties.getFile()),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Event event : events) {
                writer.write(eventJoin(event));

                writer.newLine();
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private String eventJoin(Event event) {

        return new StringJoiner(", ")
                .add(String.valueOf(event.id()))
                .add(event.name())
                .add(event.description())
                .add(event.start().toString())
                .add(event.end().toString())
                .toString();
    }

}
