# System Design Implementations

> "System design need not to be theoretical - learn system design by actually coding"

A collection of practical system design implementations that demonstrate real-world architectural patterns and solutions through working code.

## Projects

### 1. Document Service
**Location**: `./document-service/`

A Spring Boot application demonstrating a complete document management system with cloud storage integration.

**Key Features**:
- Pre-signed URL generation for secure file uploads/downloads
- S3 integration for scalable file storage
- Document metadata management
- RESTful API design
- Spring Boot 3.2.0 with modern Java practices

**System Design Concepts**:
- Cloud storage patterns
- Pre-signed URL security model
- Microservice architecture
- API design best practices
- Separation of concerns

**Tech Stack**: Java 17, Spring Boot, AWS S3, Gradle

---

### 2. Unique Visitors Service
**Location**: `./unique_visitors/`

A Spring Boot application implementing efficient unique visitor tracking using Redis HyperLogLog data structure.

**Key Features**:
- HyperLogLog for memory-efficient unique counting
- Time-range based visitor analytics
- High-performance visitor tracking
- Comprehensive health monitoring
- Embedded Redis for testing

**System Design Concepts**:
- Probabilistic data structures
- Memory-efficient counting algorithms
- Time-series data handling
- Caching strategies
- Monitoring and observability

**Tech Stack**: Java 17, Spring Boot, Redis, HyperLogLog, Gradle

## Getting Started

Each project contains its own README with detailed setup instructions, API documentation, and usage examples.

1. Navigate to the project directory
2. Follow the project-specific README instructions
3. Run `./gradlew bootRun` to start the service

## Learning Objectives

This repository demonstrates practical implementations of:
- Microservice architecture patterns
- Cloud storage integration
- Efficient data structures for scale
- RESTful API design
- Modern Java development practices
- Testing strategies
- Monitoring and health checks

## Contributing

Feel free to contribute additional system design implementations that showcase different architectural patterns and solutions.
