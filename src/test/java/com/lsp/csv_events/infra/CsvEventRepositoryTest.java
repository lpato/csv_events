package com.lsp.csv_events.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lsp.csv_events.domain.Event;

@ExtendWith(MockitoExtension.class)
class CsvEventRepositoryTest {

    @Mock
    private CsvEventReader reader;

    @Mock
    private CsvEventWriter writer;

    @InjectMocks
    private CsvEventRepository repository;

    private Event event1;
    private Event event2;

    @BeforeEach
    void setUp() {
        event1 = new Event(1L, "Event A", null, null, null);
        event2 = new Event(2L, "Event B", null, null, null);
    }

    @Test
    void findAll_shouldReturnAllEvents() {
        // given
        when(reader.read()).thenReturn(List.of(event1, event2));

        // when
        List<Event> result = repository.findAll();

        // then
        assertThat(result)
                .hasSize(2)
                .containsExactly(event1, event2);

        verify(reader).read();
        verifyNoInteractions(writer);
    }

    @Test
    void findById_shouldReturnMatchingEvent_whenFound() {
        // given
        when(reader.read()).thenReturn(List.of(event1, event2));

        // when
        Optional<Event> result = repository.findById(2L);

        // then
        assertThat(result)
                .isPresent()
                .contains(event2);

        verify(reader).read();
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        // given
        when(reader.read()).thenReturn(List.of(event1));

        // when
        Optional<Event> result = repository.findById(99L);

        // then
        assertThat(result).isEmpty();

        verify(reader).read();
    }

    @Test
    void findById_shouldHandleNullId() {
        // given
        when(reader.read()).thenReturn(List.of(event1));

        // when
        Optional<Event> result = repository.findById(null);

        // then
        assertThat(result).isEmpty();

        verify(reader).read();
    }

    @Test
    void saveAll_shouldDelegateToWriter() {
        // given
        List<Event> events = List.of(event1, event2);

        // when
        repository.saveAll(events);

        // then
        verify(writer).write(events);
        verifyNoInteractions(reader);
    }
}
