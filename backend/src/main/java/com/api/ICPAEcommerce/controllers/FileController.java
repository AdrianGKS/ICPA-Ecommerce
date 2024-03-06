package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.file.DownloadRequestResult;
import com.api.ICPAEcommerce.domain.file.UpdateDocumentRequest;
import com.api.ICPAEcommerce.domain.file.UpdateImageRequest;
import com.api.ICPAEcommerce.domain.file.UploadResquestResult;
import com.api.ICPAEcommerce.repositories.FileReferenceRepository;
import com.api.ICPAEcommerce.services.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "File Requests")
@AllArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final FileReferenceRepository fileReferenceRepository;


    @Operation(summary = "Rota responsável pelo upload de documentos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ID de referência e URL de upload.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UploadResquestResult.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            )
    })
    @PostMapping("/documents")
    public UploadResquestResult newDocumentUploadRequest(@RequestBody @Valid UpdateDocumentRequest request) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }

    @Operation(summary = "Rota responsável pelo upload de imagens")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ID de referência e URL de upload.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UploadResquestResult.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            )
    })
    @PostMapping("/images")
    public UploadResquestResult newImageUploadRequest(@RequestBody @Valid UpdateImageRequest request) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }

    @Operation(summary = "Rota responsável pelo download de arquivos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "URL de download mostrando o arquivo.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DownloadRequestResult.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Não encontrado.",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)
                    )
                    }
            )
    })
    @GetMapping("/downloads/{fileReferenceId}/{fileName}")
    public ResponseEntity<Void> downloadRequest (@PathVariable Long fileReferenceId) {
        var fileReference = fileReferenceRepository.findById(fileReferenceId)
                .orElseThrow(EntityNotFoundException::new);

        if (fileReference.isPublicAccessible()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DownloadRequestResult requestResult = storageService.generateDownloadUrl(fileReference);

        HttpHeaders headers =  new HttpHeaders();
        headers.add("Location", requestResult.downloadSignedUrl());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

}
