package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.order.OrderDTO;
import com.api.ICPAEcommerce.domain.order.Order;
import com.api.ICPAEcommerce.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

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

        if (orderOptional == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro pelo ID " + id + " inexistente!");
        } else {
            BeanUtils.copyProperties(orderDTO, orderOptional);
            orderOptional.get().setStatus(orderDTO.status());
            orderRepository.save(orderOptional.get());

            return ResponseEntity.status(HttpStatus.OK).body("Status do ID " + id + " alterado para "+ orderDTO.status() +" com sucesso!");
        }

    }

}
