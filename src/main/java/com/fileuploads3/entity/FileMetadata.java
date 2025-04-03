package com.fileuploads3.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, unique = true)
    private String fileUrl;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private long fileSize; // In bytes

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    // Default Constructor (Required by JPA)
    public FileMetadata() {}

    // Parameterized Constructor
    public FileMetadata(String fileName, String fileUrl, String fileType, long fileSize, LocalDateTime uploadedAt) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
