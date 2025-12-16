package com.lsp.csv_events.infra;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.lsp.csv_events.domain.Event;

class CsvEventReaderTest {

    @Mock
    private CsvEventProperties csvEventProperties;

    @InjectMocks
    private CsvEventReader csvEventReader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadValidCsvFile() throws IOException {
        Path csvFile = tempDir.resolve("events.csv");
        Files.write(csvFile, "1,Event1,Description1,2024-01-01T10:00:00,2024-01-01T11:00:00\n".getBytes());

        when(csvEventProperties.getFile()).thenReturn(csvFile.toString());

        List<Event> events = csvEventReader.read();

        assertEquals(1, events.size());
        assertEquals(1L, events.get(0).id());
        assertEquals("Event1", events.get(0).name());
    }

    @Test
    void testReadCsvWithBlankLines() throws IOException {
        Path csvFile = tempDir.resolve("events.csv");
        Files.write(csvFile,
                "1,Event1,Description1,2024-01-01T10:00:00,2024-01-01T11:00:00\n\n2,Event2,Description2,2024-01-02T10:00:00,2024-01-02T11:00:00\n"
                        .getBytes());

        when(csvEventProperties.getFile()).thenReturn(csvFile.toString());

        List<Event> events = csvEventReader.read();

        assertEquals(2, events.size());
    }

    @Test
    void testReadEmptyCsvFile() throws IOException {
        Path csvFile = tempDir.resolve("events.csv");
        Files.write(csvFile, "".getBytes());

        when(csvEventProperties.getFile()).thenReturn(csvFile.toString());

        List<Event> events = csvEventReader.read();

        assertTrue(events.isEmpty());
    }

    @Test
    void testReadNonExistentFile() {
        when(csvEventProperties.getFile()).thenReturn("/nonexistent/file.csv");

        assertThrows(java.io.UncheckedIOException.class, () -> csvEventReader.read());
    }
}