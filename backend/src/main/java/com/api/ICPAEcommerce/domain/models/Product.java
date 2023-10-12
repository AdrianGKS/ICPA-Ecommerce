package com.api.ICPAEcommerce.domain.models;

import com.api.ICPAEcommerce.domain.dtos.ImageDTO;
import com.api.ICPAEcommerce.domain.dtos.ProductDTO;
import com.api.ICPAEcommerce.domain.enums.EnumProductCategory;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "products")
@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private EnumProductCategory enumProductCategory;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    public Product(ProductDTO productDTO) {
        this.code = productDTO.getCode();
        this.title = productDTO.getTitle();
        this.description = productDTO.getDescription();

        if (productDTO.getImage() != null && !productDTO.getImage().isEmpty()) {

            for (ImageDTO imageDTO : productDTO.getImage()) {

                Image image = new Image(imageDTO);
                image.setProduct(this);
                this.images.add(image);
            }
        }

        this.price = productDTO.getPrice();
        this.quantity = productDTO.getQuantity();
        this.enumProductCategory = productDTO.getEnumProductCategory();
    }

    public void update(ProductDTO productDTO) {

        if (productDTO.getCode() != null) {
            this.code = productDTO.getCode();
        }
        if (productDTO.getTitle() != null) {
            this.title = productDTO.getTitle();
        }
        if (productDTO.getDescription() != null) {
            this.description = productDTO.getDescription();
        }
        if (productDTO.getPrice() != 0) {
            this.price = productDTO.getPrice();
        }
        if (productDTO.getQuantity() != 0) {
            this.quantity = productDTO.getQuantity();
        }
        if (productDTO.getEnumProductCategory() != null) {
            this.enumProductCategory = productDTO.getEnumProductCategory();
        }

    }
}
