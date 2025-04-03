package com.fileuploads3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fileuploads3.entity.FileMetadata;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long>{

	Optional<FileMetadata> findByFileUrl(String fileUrl);
	Optional<FileMetadata> findByFileName(String fileName);
	
	void deleteById(Long id);
	
}
