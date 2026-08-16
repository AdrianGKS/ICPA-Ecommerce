package com.api.ICPAEcommerce.domain.product;

import com.api.ICPAEcommerce.domain.order.Order;
import com.api.ICPAEcommerce.dto.product.ProductDTO;
import com.api.ICPAEcommerce.dto.product.UpdateProductDTO;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
/** Entidade de Produto
 *
 * @author Adrian Gabriel K. dos Santos
 */

@Table(name = "products")
@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String code;
    private String name;
    private String description;
    @Column(precision = 19, scale = 2)
    private BigDecimal price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private EnumProductCategory enumProductCategory;

    @ManyToOne
    private Order order;

    public Product(ProductDTO productDTO) {
        this.code = productDTO.code();
        this.name = productDTO.name();
        this.description = productDTO.description();
        this.price = productDTO.price();
        this.quantity = productDTO.quantity();
        this.enumProductCategory = productDTO.enumProductCategory();
    }

    public void update(UpdateProductDTO productDTO) {
        if (productDTO.code() != null) {
            this.code = productDTO.code();
        }
        if (productDTO.name() != null) {
            this.name = productDTO.name();
        }
        if (productDTO.description() != null) {
            this.description = productDTO.description();
        }
        if (productDTO.price() != null) {
            this.price = productDTO.price();
        }
        if (productDTO.quantity() != null) {
            this.quantity = productDTO.quantity();
        }
    }
}
