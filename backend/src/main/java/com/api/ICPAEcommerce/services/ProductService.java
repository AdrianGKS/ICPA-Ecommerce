package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.*;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

/** Classe de serviços para regras de negócio sobre Produtos
 *
 * @author Adrian Gabriel K. dos Santos
 */

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /** Registro de produto
     *
     * @return 201 - produto criado
     */
    @Transactional
    public ResponseEntity register(ProductDTO productDTO, UriComponentsBuilder builder) {
        if (productRepository.existsByCode(productDTO.code())) {
            return ResponseEntity.badRequest().body("Código já cadastrado!");
        }
        var product = new Product(productDTO);
        productRepository.save(product);

        var uri =  builder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailProductDTO(product));
    }

    /** Listagem de todos os produtos
     *
     * @return 200 - lista de todos produtos
     */
    public ResponseEntity<Page<ListProductDTO>> listProducts(Pageable pageable) {
        var products = productRepository.findAll(pageable).map(ListProductDTO::new);

        return ResponseEntity.ok(products);
    }

    /** Listagem de produtos pelo nome
     *
     * @return 200 - lista de produtos pelo nome
     */
    public ResponseEntity<Page<ListProductDTO>> listProductsByName(String name, Pageable pageable) {
        var products = productRepository.findByNamePart(name, pageable).map(ListProductDTO::new);

        return ResponseEntity.ok(products);
    }

    /** Listagem de produtos por categoria
     *
     * @return 200 - lista de produtos pela categoria
     */
    public ResponseEntity<Page<ListProductDTO>> listProductsByCategory(EnumProductCategory category, Pageable pageable) {
        var products = productRepository.findProductsByCategory(category, pageable).map(ListProductDTO::new);

        return ResponseEntity.ok(products);
    }

    /** Listagem de produtos por código
     *
     * @return 200 - produto por código
     */
    public ResponseEntity listProductByCode(String code) {
        var product = productRepository.findByCode(code);

        return ResponseEntity.ok(product);
    }

    /** Valor total do estoque
     *
     * @return 200 - valor total do estoque
     */
    public ResponseEntity totalStockValue() {
        if (productRepository.totalStockValue() == null) {
            return ResponseEntity.ok(new TotalStockDTO(new BigDecimal("0.00")));
        }
        var totalValue =  productRepository.totalStockValue();

        return ResponseEntity.ok(new TotalStockDTO(totalValue).toString());
    }

    /** Atualização de informações de um produtos
     *
     * @return 200 - produto com infos atualizadas
     */
    @Transactional
    public ResponseEntity updateProduct(Long id, UpdateProductDTO productDTO) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        // Copia as propriedades atualizáveis do DTO para a entidade existente
        BeanUtils.copyProperties(productDTO, existingProduct);

        var product = productRepository.save(existingProduct);

        return ResponseEntity.ok(new DetailProductDTO(product));
    }

    /** Deleção de produto por código
     *
     * @return 204 - sem conteúdo/produto é deletado
     */
    @Transactional
    public ResponseEntity deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
