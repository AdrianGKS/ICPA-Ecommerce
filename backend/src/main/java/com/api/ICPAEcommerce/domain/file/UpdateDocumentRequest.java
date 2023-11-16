package com.api.ICPAEcommerce.domain.file;

import com.api.ICPAEcommerce.domain.file.validation.AllowedContentTypes;
import com.api.ICPAEcommerce.domain.file.validation.AllowedFileExtensions;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDocumentRequest (
        @NotBlank
        @AllowedFileExtensions("pdf")
        String fileName,

        @NotBlank
        @AllowedContentTypes("application/pdf")
        String contentType,

        @NotNull
        @Min(1)
        Long contentLength
){

    public FileReference toDomain() {
        return FileReference.builder()
                .name(fileName)
                .contentType(contentType)
                .contentLength(contentLength)
                .type(Type.DOCUMENT)
                .build();
    }
}
