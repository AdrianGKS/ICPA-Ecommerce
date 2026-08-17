package com.api.ICPAEcommerce.domain.product;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void productStoresCatalogData() {
        Product product = new Product();
        product.setId(1L); product.setCode("P-1"); product.setName("Book"); product.setDescription("Description");
        product.setPrice(new BigDecimal("12.50")); product.setQuantity(4); product.setEnumProductCategory(EnumProductCategory.BOOKS);
        product.setActive(true);
        assertAll(() -> assertEquals(1L, product.getId()), () -> assertEquals("P-1", product.getCode()),
                () -> assertEquals("Book", product.getName()), () -> assertEquals(new BigDecimal("12.50"), product.getPrice()),
                () -> assertEquals(4, product.getQuantity()), () -> assertEquals(EnumProductCategory.BOOKS, product.getEnumProductCategory()),
                () -> assertTrue(product.getActive()));
    }

    @Test
    void deactivateMarksProductInactive() {
        Product product = new Product(); product.setActive(true);
        product.deactivate();
        assertFalse(product.getActive());
    }

    @Test
    void productsWithSameIdAreEqual() {
        Product first = new Product(); first.setId(1L); Product second = new Product(); second.setId(1L);
        assertEquals(first, second);
    }
}
