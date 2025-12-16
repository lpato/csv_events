package com.lsp.csv_events.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lsp.csv_events.domain.Event;

class CsvEventWriterTest {

    @Mock
    private CsvEventProperties csvEventProperties;

    @InjectMocks
    private CsvEventWriter csvEventWriter;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldWriteEventsToFile() throws Exception {
        Path csvFile = tempDir.resolve("events.csv");
        when(csvEventProperties.getFile()).thenReturn(csvFile.toString());

        Event event = new Event(1L, "Test Event", "Description",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        List<Event> events = List.of(event);

        csvEventWriter.write(events);

        assertTrue(Files.exists(csvFile));
        List<String> lines = Files.readAllLines(csvFile);
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("Test Event"));
    }

    @Test
    void shouldTruncateExistingFile() throws Exception {
        Path csvFile = tempDir.resolve("events.csv");
        Files.writeString(csvFile, "old content");

        when(csvEventProperties.getFile()).thenReturn(csvFile.toString());

        Event event = new Event(1L, "New Event", "New Description",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        csvEventWriter.write(List.of(event));

        List<String> lines = Files.readAllLines(csvFile);
        assertEquals(1, lines.size());
        assertFalse(lines.get(0).contains("old content"));
    }
}