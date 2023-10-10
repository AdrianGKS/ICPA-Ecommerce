package com.api.ICPAEcommerce.repositories;

import com.api.ICPAEcommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByTitleIgnoreCase(String title);

    Optional<Product> findByCodeIgnoreCase(String code);

}
