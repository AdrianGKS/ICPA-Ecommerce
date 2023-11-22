package com.api.ICPAEcommerce.infra.s3;

import com.api.ICPAEcommerce.domain.file.FileReference;
import com.api.ICPAEcommerce.services.CloudStorageProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
public class S3CloudStorageProvider implements CloudStorageProvider {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final StorageProperties storageProperties;

    @Override
    public URL generatePresignedUploadUrl(FileReference fileReference) {
        var builder = AwsRequestOverrideConfiguration.builder();

        if (fileReference.isPublicAccessible()) {
            builder.putRawQueryParameter("x-amz-acl", "public-read");
        }

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(getBucket())
                .key(fileReference.getPath())
                .contentType(fileReference.getContentType())
                .contentLength(fileReference.getContentLength())
                .acl(fileReference.isPublicAccessible() ? "public-read" : null)
                .overrideConfiguration(builder.build())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .putObjectRequest(objectRequest)
                .build();


        return s3Presigner.presignPutObject(presignRequest).url();
    }

    @Override
    public URL generatePresignedDownloadUrl(FileReference fileReference) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(getBucket())
                .key(fileReference.getPath())
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url();
    }

    private String getBucket() {
        return storageProperties.getS3().getBucket();
    }
}
