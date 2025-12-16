# 📦 CSV Events Service

A Spring Boot application that manages Events via REST APIs. It supports CSV-based input/output using a configurable filesystem path.

## 🧭 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#architectureproject-structure)
- [Configuration](#configuration)
- [CSV Configuration Properties](#CSV-configuration-properties)
- [REST API](#REST-API)
- [How to Run](#how-to-run)


## 🚀 Features

- REST API for creating and retrieving events
- Batch POST of events (List<Event>)
- CSV read/write using filesystem paths

## 🛠 Tech Stack

- Java 17+
- Spring Boot 3
- Spring Web
- JUnit 5
- Mockito
- WebMvcTest

## 📂 Project Structure

```
.
├── data
│   └── events.csv
├── HELP.md
├── http
│   └── api.http
├── mvnw
├── mvnw.cmd
├── pom.xml
├── project-structure.txt
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── lsp
    │   │           └── csv_events
    │   │               ├── api
    │   │               │   └── EventController.java
    │   │               ├── CsvEventsApplication.java
    │   │               ├── domain
    │   │               │   ├── Event.java
    │   │               │   ├── EventRepository.java
    │   │               │   └── EventService.java
    │   │               └── infra
    │   │                   ├── CsvEventProperties.java
    │   │                   ├── CsvEventReader.java
    │   │                   ├── CsvEventRepository.java
    │   │                   └── CsvEventWriter.java
    │   └── resources
    │       ├── application-prod.yml
    │       ├── application.properties
    │       ├── application.yml
    │       └── META-INF
    │           └── additional-spring-configuration-metadata.json
    └── test
        └── java
            └── com
                └── lsp
                    └── csv_events
                        ├── api
                        │   └── EventControllerTest.java
                        ├── CsvEventsApplicationTests.java
                        └── infra
                            ├── CsvEventReaderTest.java
                            ├── CsvEventRepositoryTest.java
                            └── CsvEventWriterTest.java

```


## ⚙️ Configuration

application.yml

```batch
app:
  csv:
    file: ./data/events.csv
```
- The path is relative to the working directory
- The file is readable and writable
- The directory will be created automatically if missing

### Production example

```batch
app:
  csv:
    file: /var/app/data/events.csv
```

## 🧩 CSV Configuration Properties

```batch
@ConfigurationProperties(prefix = "app.csv")
public class CsvProperties {
    private Path file;
}
```
This allows easy override per environment and in tests.

## 📡 REST API

### Create events (batch): POST /events

```http
POST http://localhost:8080/events
Content-Type: application/json

[
  {
    "name": "User Registration",
    "description": "Triggered when a new user registers",
    "start": "2025-12-16T09:00:00",
    "end": "2025-12-16T09:05:00"
  }
]
```

### Response
```json
[
  {
    "id": 1,
    "name": "User Registration",
    "description": "Triggered when a new user registers",
    "start": "2025-12-16T09:00:00",
    "end": "2025-12-16T09:05:00"
  }
]
```

### Get all events: GET /events
```
GET http://localhost:8080/events
```

### Response
```json
[
  {
    "id": 1,
    "name": "User Registration",
    "description": "Triggered when a new user registers",
    "start": "2025-12-16T09:00:00",
    "end": "2025-12-16T09:05:00"
  }
]
```


## ▶️ How to Run

### Build & run:

```bash
mvn clean spring-boot:run
```

### Service will start on:

```bash
http://localhost:8080
```



