package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.order.OrderDTO;
import com.api.ICPAEcommerce.repositories.OrderRepository;
import com.api.ICPAEcommerce.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/orders")
@Validated
@SecurityRequirement(name = "bearer-key")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        var order = orderService.saveOrder(orderDTO);

        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/list-orders")
    @ResponseBody
    public ResponseEntity listOrders() {
        var orders = orderRepository.findAll();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/detail-order/{id}")
    @ResponseBody
    public ResponseEntity detailOrder(@PathVariable Long id) {
        var order = orderRepository.findById(id);

        return ResponseEntity.ok(order);
    }

    @PutMapping("/update-order-status/{id}")
    public ResponseEntity updateOrderStatus(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        var order = orderService.updateOrder(id, orderDTO);

        return ResponseEntity.ok().body(order);
    }

}
