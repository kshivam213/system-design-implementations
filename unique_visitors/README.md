# Unique Visitors Service

A Spring Boot application for tracking unique visitors using Redis HyperLogLog with health check endpoints.

## Features

- Spring Boot 3.1.5 with Java 17
- Gradle build system
- Redis HyperLogLog for efficient unique visitor counting
- Time-range based visitor tracking (daily/hourly)
- REST API endpoints for tracking and counting
- Health check REST API endpoints
- Spring Boot Actuator for monitoring
- Comprehensive test coverage with embedded Redis

## API Endpoints

### Unique Visitor Tracking Endpoints

- **POST /api/visitors/track** - Track a user visit
  ```json
  {
    "userId": "user123",
    "timeRange": "2024-08-10"  // Optional: defaults to current day
  }
  ```
  Response:
  ```json
  {
    "message": "User visit tracked successfully",
    "userId": "user123",
    "timeRange": "2024-08-10",
    "timestamp": "2024-08-10T22:30:00"
  }
  ```

- **GET /api/visitors/count?timeRange=2024-08-10** - Get unique visitor count for specific time range
  ```json
  {
    "uniqueCount": 1250,
    "timeRangeStart": "2024-08-10",
    "timeRangeEnd": "2024-08-10",
    "timestamp": "2024-08-10T22:30:00"
  }
  ```

- **POST /api/visitors/count** - Get unique visitor count for time range
  ```json
  {
    "timeRangeStart": "2024-08-10",
    "timeRangeEnd": "2024-08-12"
  }
  ```
  Response:
  ```json
  {
    "uniqueCount": 3750,
    "timeRangeStart": "2024-08-10",
    "timeRangeEnd": "2024-08-12",
    "timestamp": "2024-08-10T22:30:00"
  }
  ```

### Health Check Endpoints

- **GET /api/health** - Returns detailed health status
  ```json
  {
    "status": "UP",
    "timestamp": "2024-08-10T21:45:00",
    "service": "unique-visitors",
    "version": "1.0.0"
  }
  ```

- **GET /api/ping** - Simple ping endpoint
  ```
  pong
  ```

### Actuator Endpoints

- **GET /actuator/health** - Spring Boot Actuator health endpoint
- **GET /actuator/info** - Application information
- **GET /actuator/metrics** - Application metrics

## Getting Started

### Prerequisites

- Java 17 or higher
- Redis server (for production) or use embedded Redis for testing
- No need to install Gradle (uses Gradle wrapper)

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
./gradlew bootRun
```

The application will start on port 8080.

### Running Tests

```bash
./gradlew test
```

### Building the Application

```bash
./gradlew build
```

This will create a JAR file in `build/libs/` directory.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/design/uniquevisitors/
│   │       ├── UniqueVisitorsApplication.java
│   │       ├── config/
│   │       │   └── RedisConfig.java
│   │       ├── controller/
│   │       │   ├── HealthController.java
│   │       │   └── UniqueVisitorController.java
│   │       ├── dto/
│   │       │   ├── TrackVisitorRequest.java
│   │       │   ├── TrackVisitorResponse.java
│   │       │   ├── UniqueCountRequest.java
│   │       │   └── UniqueCountResponse.java
│   │       └── service/
│   │           └── UniqueVisitorService.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── com/design/uniquevisitors/
            ├── UniqueVisitorsApplicationTests.java
            └── controller/
                └── HealthControllerTest.java
```

## Configuration

The application can be configured through `src/main/resources/application.yml`:

- Server port: 8080
- Management endpoints are exposed
- Logging configuration
- Spring profiles support

## Development

This project uses:
- Spring Boot 3.1.5
- Spring Web for REST APIs
- Spring Boot Actuator for monitoring
- JUnit 5 for testing
- Lombok for reducing boilerplate code
>>>>>>> e25f9c6 (first commit)
