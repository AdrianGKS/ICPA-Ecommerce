package com.api.ICPAEcommerce.repositories;

import com.api.ICPAEcommerce.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
