package com.api.ICPAEcommerce.domain.file;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "FileReference")
@Table (name = "file_reference")
public class FileReference {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private String name;
    private String contentType;
    private Long contentLength;

    @Builder.Default
    private Boolean temp = true;

    @Enumerated(EnumType.STRING)
    private Type type;

    public FileReference(FileReferenceDTO fileReferenceDTO) {
        this.id = fileReferenceDTO.id();
        this.name = fileReferenceDTO.name();
        this.contentType = fileReferenceDTO.contentType();
        this.contentLength = fileReferenceDTO.contentLength();
        this.type = fileReferenceDTO.type();
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }

    public boolean isPublicAccessible() {
        return this.type.isPublicAccessible();
    }

    public String getPath() {
        return this.id + "/" + this.name;
    }
}
