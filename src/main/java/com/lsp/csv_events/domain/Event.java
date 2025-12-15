package com.lsp.csv_events.domain;

import java.time.LocalDateTime;

public record Event(
        Long id,
        String name,
        String description,
        LocalDateTime start,
        LocalDateTime end) {
}
