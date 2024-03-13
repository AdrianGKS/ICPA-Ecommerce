package com.api.ICPAEcommerce.domain.file;

import com.api.ICPAEcommerce.domain.product.Product;
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

    @ManyToOne
    private Product product;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Setter
    @Builder.Default
    private Boolean temp = true;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String name;
    private String contentType;
    private Long contentLength;

    public FileReference(FileReferenceDTO fileReferenceDTO) {
        this.id = fileReferenceDTO.id();
        this.name = fileReferenceDTO.name();
        this.contentType = fileReferenceDTO.contentType();
        this.contentLength = fileReferenceDTO.contentLength();
        this.type = fileReferenceDTO.type();
    }

    public boolean isPublicAccessible() {
        return this.type.isPublicAccessible();
    }

    public String getPath() {
        return this.id + "/" + this.name;
    }
}
