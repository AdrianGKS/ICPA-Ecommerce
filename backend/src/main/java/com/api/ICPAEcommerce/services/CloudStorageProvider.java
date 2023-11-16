package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.file.FileReference;

import java.net.URL;


public interface CloudStorageProvider {
    URL generatePresignedUploadUrl(FileReference fileReference);
}
