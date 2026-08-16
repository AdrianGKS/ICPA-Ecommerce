package com.api.ICPAEcommerce.domain.product;

import com.api.ICPAEcommerce.dto.product.ProductDTO;
import com.api.ICPAEcommerce.dto.product.UpdateProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Entity Tests")
class ProductTest {

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO(
            "PRD-0001",
            "Oculos de Sol",
            "Oculos de Sol de alta qualidade, modelo 2024",
            new BigDecimal("918.17"),
            4,
            EnumProductCategory.BOOKS
        );
    }

    @Test
    @DisplayName("Criar produto com sucesso via ProductDTO")
    void shouldCreateProductFromDTO() {
        product = new Product(productDTO);

        assertNotNull(product);
        assertEquals("PRD-0001", product.getCode());
        assertEquals("Oculos de Sol", product.getName());
        assertEquals("Oculos de Sol de alta qualidade, modelo 2024", product.getDescription());
        assertEquals(new BigDecimal("918.17"), product.getPrice());
        assertEquals(4, product.getQuantity());
        assertEquals(EnumProductCategory.BOOKS, product.getEnumProductCategory());
    }

    @Test
    @DisplayName("Produto deve permitir categoria BOOKS")
    void productShouldAllowBooksCategory() {
        ProductDTO dto = new ProductDTO(
            "PRD-TEST",
            "Test Product",
            "Test",
            new BigDecimal("50.00"),
            10,
            EnumProductCategory.BOOKS
        );

        product = new Product(dto);

        assertEquals(EnumProductCategory.BOOKS, product.getEnumProductCategory());
    }

    @Test
    @DisplayName("Produto deve permitir categoria MUSIC")
    void productShouldAllowMusicCategory() {
        ProductDTO dto = new ProductDTO(
            "PRD-TEST",
            "Test Product",
            "Test",
            new BigDecimal("50.00"),
            10,
            EnumProductCategory.MUSIC
        );

        product = new Product(dto);

        assertEquals(EnumProductCategory.MUSIC, product.getEnumProductCategory());
    }

    @Test
    @DisplayName("Produto deve permitir categoria CLOTHES")
    void productShouldAllowClothesCategory() {
        ProductDTO dto = new ProductDTO(
            "PRD-TEST",
            "Test Product",
            "Test",
            new BigDecimal("50.00"),
            10,
            EnumProductCategory.CLOTHES
        );

        product = new Product(dto);

        assertEquals(EnumProductCategory.CLOTHES, product.getEnumProductCategory());
    }

    @Test
    @DisplayName("Produto deve permitir categoria ACCESSORIES")
    void productShouldAllowAccessoriesCategory() {
        ProductDTO dto = new ProductDTO(
            "PRD-TEST",
            "Test Product",
            "Test",
            new BigDecimal("50.00"),
            10,
            EnumProductCategory.ACCESSORIES
        );

        product = new Product(dto);

        assertEquals(EnumProductCategory.ACCESSORIES, product.getEnumProductCategory());
    }

    @Test
    @DisplayName("Atualizar código do produto")
    void shouldUpdateProductCode() {
        product = new Product(productDTO);
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            "PRD-0002",
            null,
            null,
            null,
            null,
            null
        );

        product.update(updateDTO);

        assertEquals("PRD-0002", product.getCode());
        assertEquals("Oculos de Sol", product.getName());
    }

    @Test
    @DisplayName("Atualizar nome do produto mantém código anterior")
    void shouldUpdateProductNameKeepCode() {
        product = new Product(productDTO);
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            null,
            "Vinil Classico",
            null,
            null,
            null,
            null
        );

        product.update(updateDTO);

        assertEquals("PRD-0001", product.getCode());
        assertEquals("Vinil Classico", product.getName());
    }

    @Test
    @DisplayName("Atualizar descrição do produto")
    void shouldUpdateProductDescription() {
        product = new Product(productDTO);
        String newDescription = "Nova descrição";
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            null,
            null,
            newDescription,
            null,
            null,
            null
        );

        product.update(updateDTO);

        assertEquals(newDescription, product.getDescription());

        UpdateProductDTO updateDTO2 = new UpdateProductDTO(1L, null, null, "Updated Description", null, null, null);
        product.update(updateDTO2);
        assertEquals("Updated Description", product.getDescription());
    }

    @Test
    @DisplayName("Atualizar preço do produto")
    void shouldUpdateProductPrice() {
        product = new Product(productDTO);
        BigDecimal newPrice = new BigDecimal("1500.00");
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            null,
            null,
            null,
            newPrice,
            null,
            null
        );

        product.update(updateDTO);

        assertEquals(newPrice, product.getPrice());
    }

    @Test
    @DisplayName("Atualizar quantidade do produto")
    void shouldUpdateProductQuantity() {
        product = new Product(productDTO);
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            null,
            null,
            null,
            null,
            15,
            null
        );

        product.update(updateDTO);

        assertEquals(15, product.getQuantity());
    }

    @Test
    @DisplayName("Atualizar múltiplos campos do produto")
    void shouldUpdateMultipleProductFields() {
        product = new Product(productDTO);
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            "PRD-0050",
            "Colar Prata",
            "Colar de Prata Atualizado",
            new BigDecimal("250.50"),
            20,
            null
        );

        product.update(updateDTO);

        assertEquals("PRD-0050", product.getCode());
        assertEquals("Colar Prata", product.getName());
        assertEquals("Colar de Prata Atualizado", product.getDescription());
        assertEquals(new BigDecimal("250.50"), product.getPrice());
        assertEquals(20, product.getQuantity());
    }

    @Test
    @DisplayName("Atualização com todos os campos nulos não altera produto")
    void updateWithAllNullsShouldNotChangeProduct() {
        product = new Product(productDTO);
        String originalName = product.getName();
        BigDecimal originalPrice = product.getPrice();

        UpdateProductDTO updateDTO = new UpdateProductDTO(
            1L,
            null,
            null,
            null,
            null,
            null,
            null
        );

        product.update(updateDTO);

        assertEquals(originalName, product.getName());
        assertEquals(originalPrice, product.getPrice());
    }

    @Test
    @DisplayName("Produto com preço zero")
    void productWithZeroPrice() {
        ProductDTO dto = new ProductDTO(
            "PRD-FREE",
            "Free Product",
            "This product is free",
            new BigDecimal("0.00"),
            100,
            EnumProductCategory.BOOKS
        );

        product = new Product(dto);

        assertEquals(new BigDecimal("0.00"), product.getPrice());
    }

    @Test
    @DisplayName("Produto com quantidade zero")
    void productWithZeroQuantity() {
        ProductDTO dto = new ProductDTO(
            "PRD-STOCK",
            "Out of Stock",
            "Currently out of stock",
            new BigDecimal("99.99"),
            0,
            EnumProductCategory.CLOTHES
        );

        product = new Product(dto);

        assertEquals(0, product.getQuantity());
    }

    @Test
    @DisplayName("Produto com quantidade negativa não deve ser invalidado no construtor")
    void productWithNegativeQuantityNotValidatedInConstructor() {
        ProductDTO dto = new ProductDTO(
            "PRD-NEG",
            "Negative Quantity",
            "Test",
            new BigDecimal("50.00"),
            -5,
            EnumProductCategory.ACCESSORIES
        );

        product = new Product(dto);

        assertEquals(-5, product.getQuantity());
    }

    @Test
    @DisplayName("Produto com preço negativo não deve ser invalidado no construtor")
    void productWithNegativePriceNotValidatedInConstructor() {
        ProductDTO dto = new ProductDTO(
            "PRD-NEGPRICE",
            "Negative Price",
            "Test",
            new BigDecimal("-50.00"),
            5,
            EnumProductCategory.MUSIC
        );

        product = new Product(dto);

        assertEquals(new BigDecimal("-50.00"), product.getPrice());
    }

    @Test
    @DisplayName("Dois produtos com mesmo ID devem ser iguais")
    void productsWithSameIdShouldBeEqual() {
        Product product1 = new Product(productDTO);
        product1.setId(1L);

        Product product2 = new Product(productDTO);
        product2.setId(1L);

        assertEquals(product1, product2);
    }

    @Test
    @DisplayName("Dois produtos com IDs diferentes devem ser diferentes")
    void productsWithDifferentIdsShouldNotBeEqual() {
        Product product1 = new Product(productDTO);
        product1.setId(1L);

        Product product2 = new Product(productDTO);
        product2.setId(2L);

        assertNotEquals(product1, product2);
    }

    @Test
    @DisplayName("Produto com descrição muito longa")
    void productWithVeryLongDescription() {
        String longDescription = "A".repeat(1000);
        ProductDTO dto = new ProductDTO(
            "PRD-LONG",
            "Long Description Product",
            longDescription,
            new BigDecimal("99.99"),
            10,
            EnumProductCategory.BOOKS
        );

        product = new Product(dto);

        assertEquals(longDescription, product.getDescription());
    }

    @Test
    @DisplayName("Produto com preço de alta precisão")
    void productWithHighPrecisionPrice() {
        ProductDTO dto = new ProductDTO(
            "PRD-PRECISE",
            "Precise Price Product",
            "Test",
            new BigDecimal("1234567890123456.99"),
            1,
            EnumProductCategory.ACCESSORIES
        );

        product = new Product(dto);

        assertEquals(new BigDecimal("1234567890123456.99"), product.getPrice());
    }
}











