package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.product.*;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import com.api.ICPAEcommerce.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    @Transactional
    public ResponseEntity createProduct(@RequestBody @Valid ProductDTO productDTO, UriComponentsBuilder uriBuilder) {
        var product = new Product(productDTO);
        productRepository.save(product);

        var uri =  uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailProductDTO(product));
    }

    /** End-point de listagem geral de produtos
     *
     * @return List - Lista de produtos cadastrados
     */
    @GetMapping("/list-products")
    public ResponseEntity<Page<ListProductDTO>> listProducts(@PageableDefault(sort = {"name"}) Pageable pageable) {
        var products = productRepository.findAll(pageable).map(ListProductDTO::new);

        return ResponseEntity.ok(products);
    }

    /** End-point de listagem de produto pelo nome
     *
     * @return Produto - produto cadastrado
     */
    @GetMapping("/list-products/name/{name}")
    public ResponseEntity<Page<ListProductDTO>> listProductsByName(@PathVariable String name, @PageableDefault(sort = {"name"}) Pageable pageable) {
        var products = productRepository.findByNamePart(name, pageable).map(ListProductDTO::new);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/list-product/code/{code}")
    public ResponseEntity listProductByCode(@PathVariable String code) {
        var product = productRepository.findByCode(code);

        return ResponseEntity.ok(product);
    }

    /** End-point de atualização de produto

     * @return Produto - produto atualizado
     */
    @PutMapping("/update-product")
    @Transactional
    public ResponseEntity updateProduct(@RequestBody @Valid UpdateProductDTO productDTO) {
        var product = productRepository.getReferenceById(productDTO.id());
        product.update(productDTO);

        return ResponseEntity.ok(new DetailProductDTO(product));

    }

    /** End-point de atualização de produto

     * @return 200 - mensagem de exclusão
     */
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Produto excluído com sucesso!");
    }

}
