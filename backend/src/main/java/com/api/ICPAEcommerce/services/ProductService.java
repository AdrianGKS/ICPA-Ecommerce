package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.EnumProductCategory;
import com.api.ICPAEcommerce.dto.product.DetailProductDTO;
import com.api.ICPAEcommerce.dto.product.ListProductDTO;
import com.api.ICPAEcommerce.dto.product.ProductDTO;
import com.api.ICPAEcommerce.dto.product.UpdateProductDTO;
import com.api.ICPAEcommerce.mappers.ProductMapper;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ResponseEntity register(ProductDTO productDTO, UriComponentsBuilder builder) {
        if (productRepository.existsByCode(productDTO.code())) {
            return ResponseEntity.badRequest().body("Já existe um produto cadastrado com este código.");
        }

        var product = productMapper.toEntity(productDTO);
        productRepository.save(product);

        var uri = builder.path("/api/v1/products/list-product/code/{code}")
                .buildAndExpand(product.getCode()).toUri();

        return ResponseEntity.created(uri).body(productMapper.toDetailDTO(product));
    }

    public ResponseEntity<Page<ListProductDTO>> listProducts(Pageable pageable) {
        var products = productRepository.findAllByActiveTrue(pageable).map(productMapper::toListDTO);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<Page<ListProductDTO>> listProductsByName(String name, Pageable pageable) {
        var products = productRepository.findByNameContainingIgnoreCaseAndActiveTrue(name, pageable).map(productMapper::toListDTO);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<Page<ListProductDTO>> listProductsByCategory(EnumProductCategory category, Pageable pageable) {
        var products = productRepository.findByEnumProductCategoryAndActiveTrue(category, pageable).map(productMapper::toListDTO);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity listProductByCode(String code) {
        var optional = productRepository.findByCodeAndActiveTrue(code);
        return optional.map(product -> ResponseEntity.ok().body((Object) productMapper.toDetailDTO(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity totalStockValue() {
        var totalValue = productRepository.totalStockValue();
        return ResponseEntity.ok(totalValue);
    }

    @Transactional
    public ResponseEntity updateProduct(UpdateProductDTO productDTO) {
        var optional = productRepository.findById(productDTO.id());

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var product = optional.get();
        productMapper.updateEntityFromDTO(productDTO, product);

        return ResponseEntity.ok(productMapper.toDetailDTO(product));
    }

    @Transactional
    public ResponseEntity deleteProduct(String code) {
        var optional = productRepository.findByCodeAndActiveTrue(code);

        if (optional.isPresent()) {
            var product = optional.get();
            product.deactivate();
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}