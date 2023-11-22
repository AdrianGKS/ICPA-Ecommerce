package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.file.*;
import com.api.ICPAEcommerce.repositories.FileReferenceRepository;
import com.api.ICPAEcommerce.services.StorageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/files")
@AllArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final FileReferenceRepository fileReferenceRepository;

    @PostMapping("/documents")
    public UploadResquestResult newDocumentUploadRequest(@RequestBody @Valid UpdateDocumentRequest request) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }


    @PostMapping("/images")
    public UploadResquestResult newImageUploadRequest(@RequestBody @Valid UpdateImageRequest request) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }

    @GetMapping("/downloads/{fileReferenceId}/{fileName}")
    public ResponseEntity<Void> downloadRequest (@PathVariable Long fileReferenceId) {
        var fileReference = fileReferenceRepository.findById(fileReferenceId).orElseThrow(EntityNotFoundException::new);

        if (fileReference.isPublicAccessible()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DownloadRequestResult requestResult = storageService.generateDownloadUrl(fileReference);

        HttpHeaders headers =  new HttpHeaders();
        headers.add("Location", requestResult.downloadSignedUrl());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

}
