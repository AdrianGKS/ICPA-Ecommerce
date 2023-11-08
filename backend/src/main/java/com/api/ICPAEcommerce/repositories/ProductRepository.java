package com.api.ICPAEcommerce.repositories;

import com.api.ICPAEcommerce.domain.product.EnumProductCategory;
import com.api.ICPAEcommerce.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByCode(String code);

    @Query("""
        select p from Product p where p.name like %:name%
    """)
    Page<Product> findByNamePart(@Param("name") String name, Pageable pageable);

    @Query("""
        select p from Product p where p.enumProductCategory = :category
    """)
    Page<Product> findProductsByCategory(EnumProductCategory category, Pageable pageable);

    @Query("""
        select sum(p.price) from Product p
    """)
    BigDecimal totalStockValue();

}
