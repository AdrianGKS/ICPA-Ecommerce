package com.api.ICPAEcommerce.infra.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(StorageProperties.class)
public class S3Config {

    // A dependência é injetada via construtor, substituindo múltiplos @Value
    private final StorageProperties storageProperties;

    @Bean
    public S3Client s3Client() {
        log.debug("Inicializando S3Client com endpoint: {}", storageProperties.s3().endpoint());

        return S3Client.builder()
                .endpointOverride(URI.create(storageProperties.s3().endpoint()))
                .region(Region.of(storageProperties.s3().region())) // Região dinâmica
                .credentialsProvider(getCredentialsProvider())
                .forcePathStyle(true) // Necessário para compatibilidade com MinIO
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Presigner.builder()
                .endpointOverride(URI.create(storageProperties.s3().endpoint()))
                .region(Region.of(storageProperties.s3().region()))
                .credentialsProvider(getCredentialsProvider())
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    // Metodo privado para evitar repetição do Builder de credenciais
    private StaticCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                        storageProperties.s3().keyId(),
                        storageProperties.s3().keySecret()
                )
        );
    }
}