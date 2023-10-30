package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.domain.product.ProductDTO;
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
    public ResponseEntity updateProduct(Long id, ProductDTO productDTO) {
        if (findProduct(productDTO)) {
            var product = productRepository.getReferenceById(id);
            product.update(productDTO);
            productRepository.save(product);

            return ResponseEntity.ok(new ProductDTO(product));
        }

        return ResponseEntity.ok("Código do produto ou Título já cadastrado");
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean findProduct(ProductDTO productDTO) {
        var findProductCode = productRepository.findByCodeIgnoreCase(productDTO.getCode());
        var findProductTitle = productRepository.findByTitleIgnoreCase(productDTO.getTitle());

        return findProductCode.isEmpty() && findProductTitle.isEmpty();
    }

}
