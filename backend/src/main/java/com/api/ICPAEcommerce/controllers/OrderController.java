package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.order.EnumOrderStatus;
import com.api.ICPAEcommerce.dto.order.OrderRequestDTO;
import com.api.ICPAEcommerce.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity createOrder(@RequestBody @Valid OrderRequestDTO orderDTO) {
        return orderService.saveOrder(orderDTO);
    }

    @GetMapping("/list-orders")
    public ResponseEntity listOrders() {
        return orderService.listOrders();
    }

    @GetMapping("/detail-order/{id}")
    public ResponseEntity detailOrder(@PathVariable Long id) {
        return orderService.detailOrder(id);
    }

    // Ocultado o OrderDTO completo, recebendo apenas o Status para atualização segura[cite: 18]
    @PutMapping("/update-order-status/{id}")
    public ResponseEntity updateOrderStatus(@PathVariable Long id, @RequestParam EnumOrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }
}