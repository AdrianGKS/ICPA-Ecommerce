package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.file.DownloadRequestResult;
import com.api.ICPAEcommerce.domain.file.FileReference;
import com.api.ICPAEcommerce.domain.file.UploadResquestResult;
import com.api.ICPAEcommerce.repositories.FileReferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StorageService {

    private final CloudStorageProvider storageProvider;
    private final FileReferenceRepository fileReferenceRepository;

    public UploadResquestResult generateUploadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        fileReferenceRepository.save(fileReference);
        var presignedUploadUrl = storageProvider.generatePresignedUploadUrl(fileReference);

        return new UploadResquestResult(fileReference.getId(), presignedUploadUrl.toString());
    }

    public DownloadRequestResult generateDownloadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        URL url = storageProvider.generatePresignedDownloadUrl(fileReference);
        return new DownloadRequestResult(url.toString());
    }
}
