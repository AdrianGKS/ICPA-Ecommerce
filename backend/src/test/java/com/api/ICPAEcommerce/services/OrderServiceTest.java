package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.domain.order.*;
import com.api.ICPAEcommerce.domain.orderItem.OrderItem;
import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.dto.order.OrderDetailDTO;
import com.api.ICPAEcommerce.dto.order.OrderRequestDTO;
import com.api.ICPAEcommerce.dto.orderItem.OrderItemRequestDTO;
import com.api.ICPAEcommerce.repositories.OrderRepository;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderRepository orderRepository;
    @Mock ProductRepository productRepository;
    @Mock OrderMapper orderMapper;
    @InjectMocks OrderService service;

    private final Address address = new Address("Street", "1", "City", "Center", "SP", "00000-000", null);

    private OrderRequestDTO request(int quantity) {
        return new OrderRequestDTO(List.of(new OrderItemRequestDTO(1L, quantity)), address,
                "buyer@example.com", EnumPaymenType.PIX);
    }

    @Test
    void saveOrderCalculatesTotalSnapshotsPriceAndDecrementsStock() {
        Product product = product(10, "25.50");
        OrderDetailDTO detail = new OrderDetailDTO(1L, "buyer@example.com", null, 51.0,
                EnumPaymenType.PIX, EnumOrderStatus.PENDING_PAYMENT, address, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderMapper.toDetailDTO(any(Order.class))).thenReturn(detail);

        var response = service.saveOrder(request(2));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order saved = captureSavedOrder();
        assertEquals(51.0, saved.getOrderPrice());
        assertEquals(EnumOrderStatus.PENDING_PAYMENT, saved.getStatus());
        assertEquals(8, product.getQuantity());
        assertEquals(product, saved.getItems().getFirst().getProduct());
        assertEquals(new BigDecimal("25.50"), saved.getItems().getFirst().getPriceAtTimeOfPurchase());
    }

    @Test
    void saveOrderRejectsInactiveOrInsufficientStock() {
        Product product = product(1, "10.00");
        product.setActive(false);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var response = service.saveOrder(request(2));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void saveOrderThrowsWhenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.saveOrder(request(1)));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void listAndDetailOrdersMapEntities() {
        Order order = new Order();
        OrderDetailDTO detail = new OrderDetailDTO(1L, "buyer@example.com", null, 1.0,
                EnumPaymenType.PIX, EnumOrderStatus.PENDING_PAYMENT, address, List.of());
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDetailDTO(order)).thenReturn(detail);

        assertEquals(List.of(detail), service.listOrders().getBody());
        assertEquals(detail, service.detailOrder(1L).getBody());
        assertEquals(HttpStatus.NOT_FOUND, service.detailOrder(2L).getStatusCode());
    }

    @Test
    void cancelRestoresStockOnlyOnce() {
        Product product = product(3, "10.00");
        OrderItem item = new OrderItem(); item.setProduct(product); item.setQuantity(2);
        Order order = new Order(); order.setStatus(EnumOrderStatus.PROCESSING); order.setItems(List.of(item));
        OrderDetailDTO detail = new OrderDetailDTO(1L, null, null, null, null, EnumOrderStatus.CANCELED, null, List.of());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDetailDTO(order)).thenReturn(detail);

        service.updateOrderStatus(1L, EnumOrderStatus.CANCELED);
        service.updateOrderStatus(1L, EnumOrderStatus.CANCELED);

        assertEquals(5, product.getQuantity());
        assertEquals(EnumOrderStatus.CANCELED, order.getStatus());
    }

    @Test
    void updateStatusReturnsNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.updateOrderStatus(99L, EnumOrderStatus.PROCESSING).getStatusCode());
    }

    private Product product(int quantity, String price) {
        Product p = new Product(); p.setId(1L); p.setName("Product"); p.setQuantity(quantity);
        p.setPrice(new BigDecimal(price)); p.setActive(true); return p;
    }

    private Order captureSavedOrder() {
        var captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        return captor.getValue();
    }
}
