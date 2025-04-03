# S3 File Management API

A simple Spring Boot application for managing file uploads, retrievals, and deletions using AWS S3 and MySQL.

---

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Setup](#project-setup)
- [API Endpoints](#api-endpoints)
- [AWS S3 Configuration](#aws-s3-configuration)
- [Database Schema](#database-schema)
- [Testing the API](#testing-the-api)
- [Contributing](#contributing)

---

## Introduction
This API allows users to upload files to AWS S3, retrieve metadata, download files, and delete files. The file metadata is stored in a MySQL database.

---

## Features
- Upload files to AWS S3  
- Retrieve all uploaded files  
- Download a file using its ID  
- Delete a file from AWS S3 and the database  

---

## Tech Stack
- **Spring Boot** (Java)  
- **Spring Data JPA** (MySQL)  
- **AWS SDK for Java** (S3 Storage)  
- **Spring Web** (REST API)  
- **Maven** (Build tool)  

---

## Project Setup

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/Hariharan1893/S3-File-Management.git
cd S3-File-Management
```

### **2️⃣ Configure AWS S3 & Database**
Update `application.yml` with your AWS credentials and database details:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/file_upload_api_s3
    username: root
    password: Hari@Sql18
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
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
    bucket-name: your-bucket-name
    region: your-region
  access-key: your-access-key
  secret-key: your-secret-key
```

### **3️⃣ Build & Run the Application**
```sh
mvn clean install
mvn spring-boot:run
```
The server starts at `http://localhost:8080`

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|------------|
| **POST** | `/api/files/upload` | Uploads a file to AWS S3 |
| **GET** | `/api/files/all` | Retrieves all uploaded files |
| **GET** | `/api/files/{id}` | Downloads a file by ID |
| **DELETE** | `/api/files/delete/{id}` | Deletes a file by ID |

---

## AWS S3 Configuration
Ensure your AWS S3 bucket allows file uploads. Create an IAM user with `AmazonS3FullAccess` permissions. Update your `application.yml` with the access key and secret key.

---

## Database Schema
The database stores file metadata.

**Table: `files`**  
| Column | Type | Description |
|--------|------|------------|
| `id` | `BIGINT` | Auto-generated primary key |
| `file_name` | `VARCHAR(255)` | Name of the uploaded file |
| `file_url` | `TEXT` | AWS S3 URL of the file |
| `upload_time` | `TIMESTAMP` | File upload timestamp |

---

## Testing the API
Use **Postman** or `cURL` to test the endpoints.

### **Upload a File**
**Request:**  
```sh
curl -X POST -F "file=@sample.pdf" http://localhost:8080/api/files/upload
```
**Response:**  
```json
{
  "id": 1,
  "fileName": "sample.pdf",
  "fileUrl": "https://s3.amazonaws.com/your-bucket/sample.pdf",
  "uploadTime": "2025-04-04T12:30:00"
}
```

### **Get All Files**
**Request:**  
```sh
curl -X GET http://localhost:8080/api/files/all
```

### **Download a File**
**Request:**  
```sh
curl -X GET http://localhost:8080/api/files/1 --output downloaded.pdf
```

### **🗑 Delete a File**
**Request:**  
```sh
curl -X DELETE http://localhost:8080/api/files/delete/1
```

---

## Contributing
Feel free to contribute by opening a pull request.

---
