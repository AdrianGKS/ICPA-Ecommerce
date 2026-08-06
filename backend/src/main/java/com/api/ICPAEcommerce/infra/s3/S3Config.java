package com.api.ICPAEcommerce.infra.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Config {

    // Apontando para as novas chaves exigidas pelo StorageProperties
    @Value("${aw.storage.s3.key-id}")
    private String accessKey;

    @Value("${aw.storage.s3.key-secret}")
    private String secretKey;

    @Value("${aw.storage.s3.endpoint}")
    private String endpoint;

    @Bean
    public S3Client s3Client() {
        System.out.println("[DEBUG] accessKey=" + accessKey);
        System.out.println("[DEBUG] secretKey=" + secretKey);
        System.out.println("[DEBUG] endpoint=" + endpoint);

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .forcePathStyle(true)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }
}