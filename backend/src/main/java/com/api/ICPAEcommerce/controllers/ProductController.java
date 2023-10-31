package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.product.ListProductDTO;
import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.domain.product.ProductDTO;
import com.api.ICPAEcommerce.domain.product.UpdateProductDTO;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import com.api.ICPAEcommerce.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    /** End-point de cadastro de produtos
     * @return DTO Produtos - informações salvas sobre produtos
     */
    @PostMapping("/create-product")
    public ResponseEntity createProduct(@RequestBody @Valid ProductDTO productDTO) {
        var product = productService.saveProduct(productDTO);

        return ResponseEntity.ok().body(product);
    }

    /** End-point de listagem geral de produtos
     *
     * @return List - Lista de produtos cadastrados
     */
    @GetMapping("/list-products")
    public ResponseEntity<Page<ListProductDTO>> listProducts(@PageableDefault Pageable pageable) {
        var products = productRepository.findAll(pageable).map(ListProductDTO::new);
        return ResponseEntity.ok(products);
    }

    /** End-point de listagem de produto pelo nome
     *
     * @return Produto - produto cadastrado
     */
    @GetMapping("/list-products/name/{name}")
    @ResponseBody
    public ResponseEntity searchProductsByTitle(@PathVariable String name) {
        var products = productRepository.findByNameIgnoreCase(name);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.ok("Produto não encontrado");

    }

    /** End-point de listagem de produto pelo id
     *
     * @return Produto - produto cadastrado
     */
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

    /** End-point de atualização de produto

     * @return Produto - produto atualizado
     */
    @PutMapping("/update-product")
    public ResponseEntity updateProduct(@RequestBody @Valid UpdateProductDTO productDTO) {
        var product = productRepository.getReferenceById(productDTO.id());
        product.update(productDTO);
        return ResponseEntity.ok().body(product);

    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Produto excluído com sucesso!");
    }

}
