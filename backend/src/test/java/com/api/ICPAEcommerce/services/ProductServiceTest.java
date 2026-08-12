package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.domain.product.ProductDTO;
import com.api.ICPAEcommerce.domain.product.UpdateProductDTO;
import com.api.ICPAEcommerce.domain.product.EnumProductCategory;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setCode("PROD001");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("50.00"));
        product.setQuantity(10);
        product.setEnumProductCategory(EnumProductCategory.BOOKS);

        productDTO = new ProductDTO(
            "PROD001",
            "Test Product",
            "Test Description",
            new BigDecimal("50.00"),
            10,
            EnumProductCategory.BOOKS
        );
    }

    @Test
    @DisplayName("Deve registrar novo produto com sucesso")
    void testRegisterProductSuccess() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        ResponseEntity<?> result = productService.register(productDTO, builder);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve listar produtos por código com sucesso")
    void testListProductByCodeSuccess() {
        when(productRepository.findByCode("PROD001")).thenReturn(Optional.of(product));

        ResponseEntity<?> result = productService.listProductByCode("PROD001");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(productRepository, times(1)).findByCode("PROD001");
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND quando produto não existe")
    void testListProductByCodeNotFound() {
        when(productRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        ResponseEntity<?> result = productService.listProductByCode("INVALID");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(productRepository, times(1)).findByCode("INVALID");
    }

    @Test
    @DisplayName("Deve deletar produto por código com sucesso")
    void testDeleteProductSuccess() {
        when(productRepository.findByCode("PROD001")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        ResponseEntity<?> result = productService.deleteProduct("PROD001");

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(productRepository, times(1)).findByCode("PROD001");
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND ao deletar produto inexistente")
    void testDeleteProductNotFound() {
        when(productRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        ResponseEntity<?> result = productService.deleteProduct("INVALID");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(productRepository, times(1)).findByCode("INVALID");
        verify(productRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve listar todos os produtos com paginação")
    void testListProducts() {
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        ResponseEntity<?> result = productService.listProducts(mock(Pageable.class));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Deve retornar valor total do estoque")
    void testTotalStockValue() {
        when(productRepository.totalStockValue()).thenReturn(new BigDecimal("500.00"));

        ResponseEntity<?> result = productService.totalStockValue();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(new BigDecimal("500.00"), result.getBody());
        verify(productRepository, times(1)).totalStockValue();
    }
}




