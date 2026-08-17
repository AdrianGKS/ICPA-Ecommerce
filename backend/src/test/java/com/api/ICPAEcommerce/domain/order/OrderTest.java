package com.api.ICPAEcommerce.domain.order;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.domain.orderItem.OrderItem;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void defaultOrderStartsWithEmptyItems() {
        Order order = new Order();
        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void settersStoreOrderData() {
        Order order = new Order();
        Address address = new Address("Street", "1", "City", "Center", "SP", "00000-000", "Apt");
        OffsetDateTime date = OffsetDateTime.now();
        order.setClientEmail("buyer@example.com"); order.setAddress(address); order.setOrderDate(date);
        order.setOrderPrice(10.5); order.setPaymentType(EnumPaymenType.PIX); order.setStatus(EnumOrderStatus.PROCESSING);
        order.setItems(new ArrayList<>());
        assertAll(() -> assertEquals("buyer@example.com", order.getClientEmail()), () -> assertEquals(address, order.getAddress()),
                () -> assertEquals(date, order.getOrderDate()), () -> assertEquals(10.5, order.getOrderPrice()),
                () -> assertEquals(EnumPaymenType.PIX, order.getPaymentType()), () -> assertEquals(EnumOrderStatus.PROCESSING, order.getStatus()));
    }

    @Test
    void itemsCanReferenceTheirOrder() {
        Order order = new Order(); OrderItem item = new OrderItem(); item.setOrder(order);
        order.getItems().add(item);
        assertSame(order, order.getItems().getFirst().getOrder());
    }
}
