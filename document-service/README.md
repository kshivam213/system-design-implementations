# Document Service

A Spring Boot application for document management services.

## Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher (included via Gradle wrapper)

## Getting Started

### Running the application

```bash
# Using Gradle wrapper (recommended)
./gradlew bootRun

# Or using Gradle directly
gradle bootRun
```

### Building the application

```bash
# Build the application
./gradlew build

# Build without running tests
./gradlew build -x test
```

### API Endpoints

#### Health Endpoints
- **Health Check**: `GET /api/v1/health` - Returns application health status
- **Ping**: `GET /api/v1/ping` - Simple ping endpoint that returns "pong"
- **Actuator Health**: `GET /actuator/health` - Spring Boot actuator health endpoint

#### Document Management APIs
- **Generate Upload URL**: `POST /api/v1/documents/upload-url` - Generate pre-signed S3 upload URL
- **Generate Download URL**: `GET /api/v1/documents/{documentId}/download-url` - Generate pre-signed S3 download URL
- **Get Document Metadata**: `GET /api/v1/documents/{documentId}` - Retrieve document metadata
- **List All Documents**: `GET /api/v1/documents` - Get all documents (for testing)

### Configuration

The application runs on port 8080 by default. You can modify the configuration in `src/main/resources/application.yml`.

### Development

The application is configured with:
- Spring Boot 3.2.0
- Spring Web
- Spring Boot Actuator
- Spring Boot Validation
- Spring Cloud AWS
- AWS Java SDK
- Lombok for reducing boilerplate code

### Document Management Features

The application provides a complete document management system with S3 integration:

1. **Upload Flow**:
   - User sends fileName and fileType to `/api/v1/documents/upload-url`
   - System generates a 14-character alphanumeric documentId
   - System creates S3 key: `documents/{documentId}/{fileName}`
   - System returns pre-signed upload URL (valid for 1 hour)
   - User uploads file directly to S3 using the pre-signed URL

2. **Download Flow**:
   - User sends documentId to `/api/v1/documents/{documentId}/download-url`
   - System retrieves document metadata from in-memory storage
   - System generates pre-signed download URL (valid for 1 hour)
   - User downloads file directly from S3 using the pre-signed URL

3. **Metadata Storage**:
   - Document metadata stored in-memory HashMap
   - Includes: documentId, fileName, fileType, s3Key, timestamps

### API Usage Examples

#### 1. Generate Upload URL
```bash
curl -X POST http://localhost:8080/api/v1/documents/upload-url \
  -H "Content-Type: application/json" \
  -d '{
    "fileName": "document.pdf",
    "fileType": "application/pdf"
  }'
```

Response:
```json
{
  "documentId": "ABC123DEF45678",
  "uploadUrl": "https://s3.amazonaws.com/bucket/documents/ABC123DEF45678/document.pdf?...",
  "fileName": "document.pdf",
  "fileType": "application/pdf",
  "s3Key": "documents/ABC123DEF45678/document.pdf"
}
```

#### 2. Generate Download URL
```bash
curl -X GET http://localhost:8080/api/v1/documents/ABC123DEF45678/download-url
```

Response:
```json
{
  "documentId": "ABC123DEF45678",
  "downloadUrl": "https://s3.amazonaws.com/bucket/documents/ABC123DEF45678/document.pdf?...",
  "fileName": "document.pdf",
  "fileType": "application/pdf"
}
```

#### 3. Get Document Metadata
```bash
curl -X GET http://localhost:8080/api/v1/documents/ABC123DEF45678
```

Response:
```json
{
  "documentId": "ABC123DEF45678",
  "fileName": "document.pdf",
  "fileType": "application/pdf",
  "s3Key": "documents/ABC123DEF45678/document.pdf",
  "createdAt": "2025-08-12T21:30:00",
  "updatedAt": "2025-08-12T21:30:00"
}
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/design/documentservice/
│   │       ├── DocumentServiceApplication.java
│   │       ├── config/
│   │       │   └── AwsConfig.java
│   │       ├── controller/
│   │       │   ├── DocumentController.java
│   │       │   └── HealthController.java
│   │       ├── dto/
│   │       │   ├── DownloadUrlResponse.java
│   │       │   ├── UploadUrlRequest.java
│   │       │   └── UploadUrlResponse.java
│   │       ├── model/
│   │       │   └── Document.java
│   │       ├── service/
│   │       │   └── DocumentService.java
│   │       └── util/
│   │           └── DocumentIdGenerator.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── com/design/documentservice/
```
