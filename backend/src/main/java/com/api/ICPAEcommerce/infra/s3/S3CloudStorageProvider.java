

package com.api.ICPAEcommerce.infra.s3;

import com.api.ICPAEcommerce.domain.file.FileReference;
import com.api.ICPAEcommerce.infra.exception.StorageIntegrationException; // Você deverá criar esta exceção
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor // Injeta apenas atributos 'final', prevenindo problemas futuros de design
public class S3CloudStorageProvider implements CloudStorageProvider {

    // S3Client foi removido temporariamente deste arquivo pois não estava sendo utilizado
    private final S3Presigner s3Presigner;
    private final StorageProperties storageProperties;

    @Override
    public URL generatePresignedUploadUrl(FileReference fileReference) {
        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(getBucket())
                    .key(fileReference.getPath())
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(30))
                    .putObjectRequest(objectRequest)
                    .build();

            return s3Presigner.presignPutObject(presignRequest).url();

        } catch (SdkException e) {
            log.error("Erro no AWS SDK ao gerar Presigned URL para o arquivo: {}", fileReference.getPath(), e);
            // Isolamento da infraestrutura: O Controller receberá apenas uma exceção de domínio
            throw new StorageIntegrationException("Falha ao gerar link de upload no provedor de nuvem.", e);
        }
    }

    private String getBucket() {
        return storageProperties.s3().bucket();
    }
}

//    @Override
//    public URL generatePresignedDownloadUrl(FileReference fileReference) {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(getBucket())
//                .key(fileReference.getPath())
//                .build();
//
//        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
//                .signatureDuration(Duration.ofMinutes(30))
//                .getObjectRequest(getObjectRequest)
//                .build();
//
//        return s3Presigner.presignGetObject(presignRequest).url();
//    }

