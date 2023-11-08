package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.*;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

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
        var totalValue =  productRepository.totalStockValue();

        return ResponseEntity.ok(totalValue);
    }

    /** Atualização de informações de um produtos
     *
     * @return 200 - produto com infos atualizadas
     */
    @Transactional
    public ResponseEntity updateProduct(UpdateProductDTO productDTO) {
        var product = productRepository.getReferenceById(productDTO.id());
        product.update(productDTO);

        return ResponseEntity.ok(new DetailProductDTO(product));
    }

    /** Deleção de produto por código
     *
     * @return 204 - sem conteúdo/produto é deletado
     */
    @Transactional
    public ResponseEntity deleteProduct(String code) {
        var product = productRepository.findByCode(code);

        if (product != null) {
            productRepository.delete(product);
        }

        return ResponseEntity.noContent().build();
    }

}
