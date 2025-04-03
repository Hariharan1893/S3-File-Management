# File Upload API Documentation

## Server Configuration

### `application.yml`
```yaml
server:
  port: 8080

spring:
  application:
    name: file-upload-api

  datasource:
    url: jdbc:mysql://localhost:3306/your-db-name
    username: root
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update
    show-sql: true

  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 50MB

aws:
  s3:
    bucket-name: ${your-bucket-name}
    region: ${your-region-name}
  access-key: ${your-access-key}
  secret-key: ${your-secret-key}
```

## API Endpoints

### 1. Upload File
- **Endpoint:** `POST /api/files/upload`
- **Description:** Uploads a file to AWS S3 and stores metadata in MySQL.
- **Request:**
  ```http
  POST http://localhost:8080/api/files/upload
  Content-Type: multipart/form-data
  ```
  **Form Data:**
  - `file`: (Binary file)

- **Response:**
  ```json
  {
    "id": 1,
    "fileName": "sample.pdf",
    "fileUrl": "https://s3.amazonaws.com/your-bucket/sample.pdf",
    "uploadTime": "2025-04-03T10:00:00Z"
  }
  ```

### 2. Get All Files
- **Endpoint:** `GET /api/files/all`
- **Description:** Fetches metadata of all uploaded files.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "fileName": "sample.pdf",
      "fileUrl": "https://s3.amazonaws.com/your-bucket/sample.pdf",
      "uploadTime": "2025-04-03T10:00:00Z"
    }
  ]
  ```

### 3. Get File by ID
- **Endpoint:** `GET /api/files/{id}`
- **Description:** Fetches metadata of a file by its ID.
- **Response:**
  ```json
  {
    "id": 1,
    "fileName": "sample.pdf",
    "fileUrl": "https://s3.amazonaws.com/your-bucket/sample.pdf",
    "uploadTime": "2025-04-03T10:00:00Z"
  }
  ```

### 4. Delete File by ID
- **Endpoint:** `DELETE /api/files/delete/{id}`
- **Description:** Deletes a file from AWS S3 and removes metadata from MySQL.
- **Response:**
  ```json
  {
    "message": "File deleted successfully"
  }
  ```

## Notes
- Ensure AWS credentials are properly configured in the environment variables.
- File size should not exceed **10MB**.
- Files are stored securely in AWS S3.
- MySQL database stores metadata for easy file retrieval.
- Delete operation removes the file from both S3 and MySQL.

