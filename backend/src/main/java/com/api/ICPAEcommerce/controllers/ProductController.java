package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.product.ProductDTO;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import com.api.ICPAEcommerce.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** Rest controller das requisições sobre Produtos
 * @author Adrian Gabriel K. dos Santos
 */

@RestController
@RequestMapping("/api/v2/products")
@Validated
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity createProduct(@RequestBody @Valid ProductDTO productDTO) {
        var product = productService.saveProduct(productDTO);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/list-products")
    @ResponseBody
    public ResponseEntity listProducts(@PageableDefault(size=10, sort={"id"}) Pageable pagination) {
        var page = productRepository.findAll(pagination).map(ProductDTO::new);

        if(!page.isEmpty()) {
            return ResponseEntity.ok(page);
        }

        return ResponseEntity.ok("Não há produtos cadastrados");
    }

    @GetMapping("/list-products/title/{title}")
    @ResponseBody
    public ResponseEntity searchProductsByTitle(@PathVariable String title) {
        var products = productRepository.findByTitleIgnoreCase(title);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.ok("Produto não encontrado");

    }

    @GetMapping("/list-products/id/{id}")
    @ResponseBody
    public ResponseEntity searchProductsById(@PathVariable Long id) {
        var product = productRepository.getReferenceById(id);

        return ResponseEntity.ok(new ProductDTO(product));
    }

    @GetMapping("/list-products/code/{code}")
    @ResponseBody
    public ResponseEntity searchProductsByCode(@PathVariable String code) {
        var product = productRepository.findByCodeIgnoreCase(code.toUpperCase());

        if (!product.isEmpty()) {
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.ok("Produto não encontrado");
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        var product = productService.updateProduct(id, productDTO);

        return ResponseEntity.ok().body(product);

    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Produto excluído com sucesso!");
    }

}
