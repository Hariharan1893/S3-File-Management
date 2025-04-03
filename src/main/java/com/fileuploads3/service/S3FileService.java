package com.fileuploads3.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploads3.entity.FileMetadata;
import com.fileuploads3.repository.FileMetadataRepository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3FileService {

	private final S3Client s3Client;
	private final FileMetadataRepository fileMetadataRepository;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	public S3FileService(S3Client s3Client, FileMetadataRepository fileMetadataRepository) {
		this.s3Client = s3Client;
		this.fileMetadataRepository = fileMetadataRepository;
	}

	public String uploadFile(MultipartFile file) throws IOException {

		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileName)
				.contentType(file.getContentType()).build();

		s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

		FileMetadata fileMetadata = new FileMetadata(fileName, fileUrl, file.getContentType(), file.getSize(),
				LocalDateTime.now());

		fileMetadataRepository.save(fileMetadata);

		return fileUrl;
	}

	public void deleteFileById(Long id) {
        if (fileMetadataRepository.existsById(id)) {
            fileMetadataRepository.deleteById(id);
        } else {
            throw new RuntimeException("File not found with ID: " + id);
        }
    }
}
