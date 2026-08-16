package com.api.ICPAEcommerce.dto.file;

public record UploadResquestResult(

        Long fileReferenceid,
        String uploadSignedUrl
) {
}
