package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.product.*;
import com.api.ICPAEcommerce.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/** Rest controller das requisições sobre Produtos
 * @author Adrian Gabriel K. dos Santos
 */

@RestController
@RequestMapping("/api/v2/products")
@Tag(name = "Product")
@Validated
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    /** End-point de cadastro de produtos
     */
    @PostMapping("/register-product")
    public ResponseEntity registerProduct(@RequestBody @Valid ProductDTO productDTO,
                                          UriComponentsBuilder uriBuilder) {

        return productService.register(productDTO, uriBuilder);
    }

    /** End-point de listagem geral de produtos
     */
    @GetMapping("/list-products")
    public ResponseEntity<Page<ListProductDTO>> listProducts(@PageableDefault(sort = {"name"}) Pageable pageable) {

        return productService.listProducts(pageable);
    }

    /** End-point de listagem de produto pelo nome
     */
    @GetMapping("/list-products/name/{name}")
    public ResponseEntity<Page<ListProductDTO>> listProductsByName(@PathVariable String name,
                                                                   @PageableDefault(sort = {"name"}) Pageable pageable) {

        return productService.listProductsByName(name, pageable);
    }

    /** End-point de listagem de produtos pela categoria
     */
    @GetMapping("/list-products/category/{category}")
    public ResponseEntity<Page<ListProductDTO>> listProductsByCategory(@PathVariable EnumProductCategory category,
                                                                       @PageableDefault Pageable pageable) {

        return productService.listProductsByCategory(category, pageable);
    }

    /** End-point de listagem de produto pelo código
     */
    @GetMapping("/list-product/code/{code}")
    public ResponseEntity listProductByCode(@PathVariable String code) {

        return productService.listProductByCode(code);
    }

    /** End-point de soma total do estoque
     */
    @GetMapping("/total-stock-value")
    public ResponseEntity<?> totalStockValue() {

        return productService.totalStockValue();
    }

    /** End-point de atualização de produto
     */
    @PutMapping("/update-product")
    public ResponseEntity updateProduct(@RequestBody @Valid UpdateProductDTO productDTO) {

        return productService.updateProduct(productDTO);
    }

    /** End-point de atualização de produto
     */
    @DeleteMapping("/delete-product/{code}")
        public ResponseEntity deleteProduct(@PathVariable String code) {

        return productService.deleteProduct(code);
    }
}
