package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.product.*;
import com.api.ICPAEcommerce.domain.product.ProductMapper;
import com.api.ICPAEcommerce.dto.product.*;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock ProductRepository repository;
    @Mock ProductMapper mapper;
    @InjectMocks ProductService service;

    private Product product() {
        Product p = new Product(); p.setId(1L); p.setCode("P-1"); p.setName("Book");
        p.setPrice(new BigDecimal("12.00")); p.setQuantity(3); p.setActive(true); return p;
    }

    @Test
    void registerRejectsDuplicateCode() {
        ProductDTO dto = new ProductDTO("P-1", "Book", "Description", new BigDecimal("12.00"), 3, EnumProductCategory.BOOKS);
        when(repository.existsByCode("P-1")).thenReturn(true);
        assertEquals(HttpStatus.BAD_REQUEST, service.register(dto, UriComponentsBuilder.newInstance()).getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void registerCreatesProductAndLocation() {
        ProductDTO dto = new ProductDTO("P-1", "Book", "Description", new BigDecimal("12.00"), 3, EnumProductCategory.BOOKS);
        Product product = product();
        when(mapper.toEntity(dto)).thenReturn(product);
        when(mapper.toDetailDTO(product)).thenReturn(mock(DetailProductDTO.class));
        var response = service.register(dto, UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getHeaders().getLocation()).toString().contains("P-1"));
        verify(repository).save(product);
    }

    @Test
    void listMethodsUseActiveRepositoryQueries() {
        Pageable pageable = PageRequest.of(0, 10); Page<Product> page = new PageImpl<>(List.of(product()));
        when(repository.findAllByActiveTrue(pageable)).thenReturn(page);
        when(repository.findByNameContainingIgnoreCaseAndActiveTrue("book", pageable)).thenReturn(page);
        when(repository.findByEnumProductCategoryAndActiveTrue(EnumProductCategory.BOOKS, pageable)).thenReturn(page);
        when(mapper.toListDTO(any())).thenReturn(mock(ListProductDTO.class));
        assertEquals(1, Objects.requireNonNull(service.listProducts(pageable).getBody()).getTotalElements());
        assertEquals(1, Objects.requireNonNull(service.listProductsByName("book", pageable).getBody()).getTotalElements());
        assertEquals(1, Objects.requireNonNull(service.listProductsByCategory(EnumProductCategory.BOOKS, pageable).getBody()).getTotalElements());
    }

    @Test
    void codeLookupAndDeleteOnlyUseActiveProducts() {
        Product product = product();
        when(repository.findByCodeAndActiveTrue("P-1")).thenReturn(Optional.of(product));
        when(mapper.toDetailDTO(product)).thenReturn(mock(DetailProductDTO.class));
        assertEquals(HttpStatus.OK, service.listProductByCode("P-1").getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, service.deleteProduct("P-1").getStatusCode());
        assertFalse(product.getActive());
        when(repository.findByCodeAndActiveTrue("missing")).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.deleteProduct("missing").getStatusCode());
    }

    @Test
    void updateProductReturnsNotFoundOrUpdatesEntity() {
        UpdateProductDTO dto = new UpdateProductDTO(1L, "P-2", "New", "Desc", new BigDecimal("20.00"), 4);
        Product product = product();
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.toDetailDTO(product)).thenReturn(mock(DetailProductDTO.class));
        assertEquals(HttpStatus.OK, service.updateProduct(dto).getStatusCode());
        verify(mapper).updateEntityFromDTO(dto, product);
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.updateProduct(new UpdateProductDTO(2L, null, null, null, null, null)).getStatusCode());
    }

    @Test
    void totalStockValueIsReturned() {
        when(repository.totalStockValue()).thenReturn(new BigDecimal("36.00"));
        assertEquals(new BigDecimal("36.00"), service.totalStockValue().getBody());
    }
}
