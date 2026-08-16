package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.file.FileReference;
import com.api.ICPAEcommerce.dto.file.UploadResquestResult;
import com.api.ICPAEcommerce.infra.s3.CloudStorageProvider;
import com.api.ICPAEcommerce.repositories.FileReferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private final CloudStorageProvider storageProvider;
    private final FileReferenceRepository fileReferenceRepository;

    @Transactional // Garante que se o provedor de storage falhar, o banco sofre rollback
    public UploadResquestResult generateUploadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference, "FileReference não pode ser nulo");

        log.info("Iniciando geração de URL de upload para o arquivo: {}", fileReference.getPath());

        // 1. Salva no banco de dados (o ID será gerado aqui)
        fileReferenceRepository.save(fileReference);

        // 2. Chama a infraestrutura em nuvem
        var presignedUploadUrl = storageProvider.generatePresignedUploadUrl(fileReference);

        log.info("URL de upload gerada com sucesso para ID: {}", fileReference.getId());

        return new UploadResquestResult(fileReference.getId(), presignedUploadUrl.toString());
    }
}
//    public DownloadRequestResult generateDownloadUrl(FileReference fileReference) {
//        Objects.requireNonNull(fileReference);
//        URL url = storageProvider.generatePresignedDownloadUrl(fileReference);
//        return new DownloadRequestResult(url.toString());
//    }

