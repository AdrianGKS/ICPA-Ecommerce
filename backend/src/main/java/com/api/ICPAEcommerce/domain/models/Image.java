package com.api.ICPAEcommerce.domain.models;

import com.api.ICPAEcommerce.domain.dtos.ImageDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "images")
@Entity(name = "Image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="produto_id", nullable=false)
    private Product product;

    public Image(ImageDTO imageDTO) {
        this.image = imageDTO.image();
    }

    public void update(ImageDTO imageDTO) {
        this.image = imageDTO.image();
    }
}
