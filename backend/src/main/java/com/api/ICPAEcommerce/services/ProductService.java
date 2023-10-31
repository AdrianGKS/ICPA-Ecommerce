package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.domain.product.ProductDTO;
import com.api.ICPAEcommerce.domain.product.UpdateProductDTO;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    @Transactional
    public ResponseEntity saveProduct(ProductDTO productDTO) {
        if (findProduct(productDTO)) {
            Product product = new Product(productDTO);
            productRepository.save(product);

            return ResponseEntity.ok(product);
        }

        return ResponseEntity.ok("Código do produto ou título já cadastrado");

    }

    @Transactional
    public ResponseEntity updateProduct(UpdateProductDTO productDTO) {
        var product = productRepository.getReferenceById(productDTO.id());
        product.update(productDTO);
        productRepository.save(product);

        return ResponseEntity.ok(new ProductDTO(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean findProduct(ProductDTO productDTO) {
        var findProductCode = productRepository.findByCodeIgnoreCase(productDTO.getCode());
        var findProductTitle = productRepository.findByNameIgnoreCase(productDTO.getName());

        return findProductCode.isEmpty() && findProductTitle.isEmpty();
    }

}
