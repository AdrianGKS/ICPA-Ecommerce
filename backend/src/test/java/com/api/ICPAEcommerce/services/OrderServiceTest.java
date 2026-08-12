package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.order.EnumOrderStatus;
import com.api.ICPAEcommerce.domain.order.Order;
import com.api.ICPAEcommerce.domain.order.OrderDTO;
import com.api.ICPAEcommerce.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderService Tests")
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setClientEmail("customer@example.com");
        order.setOrderPrice(100.0);
        order.setStatus(EnumOrderStatus.PENDING_PAYMENT);
        order.setOrderDate(OffsetDateTime.now());

        orderDTO = new OrderDTO(
            1L,
            null,
            null,
            EnumOrderStatus.PROCESSING,
            "customer@example.com",
            OffsetDateTime.now(),
            100.0,
            null
        );
    }

    @Test
    @DisplayName("Deve listar todos os pedidos")
    void testListOrders() {
        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.listOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("customer@example.com", result.getFirst().getClientEmail());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve encontrar pedido por ID")
    void testFindById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar empty quando pedido não existe")
    void testFindByIdNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.findById(999L);

        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve atualizar status do pedido com sucesso")
    void testUpdateOrderSuccess() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        ResponseEntity<?> result = orderService.updateOrder(1L, orderDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND quando pedido não existe para update")
    void testUpdateOrderNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> result = orderService.updateOrder(999L, orderDTO);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(orderRepository, times(1)).findById(999L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve criar novo pedido")
    void testSaveOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        ResponseEntity<?> result = orderService.saveOrder(orderDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}


