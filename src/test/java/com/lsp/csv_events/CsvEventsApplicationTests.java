package com.lsp.csv_events;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsvEventsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testContextLoadsSuccessfully() {
		assertDoesNotThrow(() -> {
			CsvEventsApplicationTests tests = new CsvEventsApplicationTests();
			tests.contextLoads();
		});
	}

}
