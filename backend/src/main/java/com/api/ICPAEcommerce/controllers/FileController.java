package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.file.UpdateDocumentRequest;
import com.api.ICPAEcommerce.domain.file.UpdateImageRequest;
import com.api.ICPAEcommerce.domain.file.UploadResquestResult;
import com.api.ICPAEcommerce.services.StorageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/files")
@AllArgsConstructor
public class FileController {

    private final StorageService storageService;
    @PostMapping("/documents")
    public UploadResquestResult newDocumentUploadRequest(@RequestBody @Valid UpdateDocumentRequest request) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }


    @PostMapping("/images")
    public UploadResquestResult newImageUploadRequest(@RequestBody @Valid UpdateImageRequest request) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }
}
