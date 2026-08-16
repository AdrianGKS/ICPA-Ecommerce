package com.api.ICPAEcommerce.dto.file;

import com.api.ICPAEcommerce.domain.file.FileReference;
import com.api.ICPAEcommerce.domain.file.Type;
import com.api.ICPAEcommerce.domain.file.validation.AllowedContentTypes;
import com.api.ICPAEcommerce.domain.file.validation.AllowedFileExtensions;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateRequest(

        @NotBlank
        @AllowedFileExtensions({"png", "jpg"})
        String fileName,

        @NotBlank
        @AllowedContentTypes({"image/jpg", "image/png"})
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
                .type(Type.IMAGE)
                .build();
    }
}
