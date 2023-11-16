package com.api.ICPAEcommerce.domain.file;

public record UploadResquestResult(

        Long fileReferenceid,
        String uploadSignedUrl
) {
}
