package com.api.ICPAEcommerce.repositories;

import com.api.ICPAEcommerce.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByCode(String code);

    @Query("""
        select p from Product p where p.name like %:name%
    """)
    Page<Product> findByNamePart(@Param("name") String name, Pageable pageable);

}
