package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.dto.order.OrderDTO;
import com.api.ICPAEcommerce.domain.order.Order;
import com.api.ICPAEcommerce.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public ResponseEntity saveOrder(OrderDTO orderDTO) {
        Order order = new Order(orderDTO);
        order.setOrderDate(OffsetDateTime.now());
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }

    @Transactional
    public ResponseEntity updateOrder(Long id, OrderDTO orderDTO) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro pelo ID " + id + " inexistente!");
        }

        Order order = orderOptional.get();
        // Atualiza apenas campos permitidos (ex.: status). Evitar copiar DTO direto para Optional
        order.setStatus(orderDTO.status());
        // se for necessário atualizar outros campos, mapear explicitamente aqui
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body(order);

    }

}
