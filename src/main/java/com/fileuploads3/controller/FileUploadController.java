package com.fileuploads3.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploads3.entity.FileMetadata;
import com.fileuploads3.repository.FileMetadataRepository;
import com.fileuploads3.service.S3FileService;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

	private final S3FileService s3FileService;
    private final FileMetadataRepository fileMetadataRepository;

    public FileUploadController(S3FileService s3FileService, FileMetadataRepository fileMetadataRepository) {
        this.s3FileService = s3FileService;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = s3FileService.uploadFile(file);

            // Fetch metadata directly from the S3FileService (already saved there)
            FileMetadata metadata = fileMetadataRepository.findByFileUrl(fileUrl)
                    .orElseThrow(() -> new RuntimeException("File metadata not found"));

            return ResponseEntity.ok(metadata);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<FileMetadata>> getAllFiles() {
        return ResponseEntity.ok(fileMetadataRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FileMetadata> getFileById(@PathVariable Long id) {
        return fileMetadataRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        s3FileService.deleteFileById(id);
        return ResponseEntity.ok("File deleted successfully");
    }

}















