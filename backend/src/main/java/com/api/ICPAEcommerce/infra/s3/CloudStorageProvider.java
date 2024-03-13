package com.api.ICPAEcommerce.infra.s3;

import com.api.ICPAEcommerce.domain.file.FileReference;

import java.net.URL;


public interface CloudStorageProvider {
    URL generatePresignedUploadUrl(FileReference fileReference);
    //URL generatePresignedDownloadUrl(FileReference fileReference);

}
