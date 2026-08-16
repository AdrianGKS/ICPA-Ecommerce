package com.api.ICPAEcommerce.domain.order;

import com.api.ICPAEcommerce.domain.product.EnumProductCategory;
import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.dto.order.OrderDTO;
import com.api.ICPAEcommerce.dto.product.ProductDTO;
import com.api.ICPAEcommerce.domain.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Entity Tests")
class OrderTest {

    private Order order;
    private OrderDTO orderDTO;
    private Address address;
    private List<ProductDTO> productDTOs;

    @BeforeEach
    void setUp() {
        address = new Address(
            "Av. Paulista",
            "1282",
            "Sao Paulo",
            "Santa Luzia",
            "SP",
            "30585-345",
            "Casa"
        );

        productDTOs = new ArrayList<>();
        productDTOs.add(new ProductDTO(
            "PRD-0001",
            "Oculos de Sol",
            "Oculos de Sol de alta qualidade, modelo 2024",
            new BigDecimal("918.17"),
            4,
            EnumProductCategory.BOOKS
        ));

        orderDTO = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user057@icpaecommerce.com",
            OffsetDateTime.parse("2026-10-10T13:19:00-03:00"),
            2864.72,
            EnumPaymenType.CREDIT_CARD
        );
    }

    @Test
    @DisplayName("Criar pedido com sucesso via OrderDTO")
    void shouldCreateOrderFromDTO() {
        order = new Order(orderDTO);

        assertNotNull(order);
        assertEquals("user057@icpaecommerce.com", order.getClientEmail());
        assertEquals(2864.72, order.getOrderPrice());
        assertEquals(EnumPaymenType.CREDIT_CARD, order.getPaymentType());
        assertNotNull(order.getAddress());
        assertNotNull(order.getItems());
        assertEquals(1, order.getItems().size());
    }

    @Test
    @DisplayName("Pedido deve estabelecer relação bidirecional com produtos")
    void orderShouldSetBidirectionalProductRelationship() {
        order = new Order(orderDTO);

        Product product = order.getItems().getFirst();
        assertNotNull(product);
        assertEquals(order, product.getOrder());
    }

    @Test
    @DisplayName("Pedido com múltiplos produtos")
    void orderWithMultipleProducts() {
        List<ProductDTO> multipleProducts = new ArrayList<>();
        multipleProducts.add(new ProductDTO("PRD-001", "Product 1", "Description 1", new BigDecimal("100.00"), 2, EnumProductCategory.BOOKS));
        multipleProducts.add(new ProductDTO("PRD-002", "Product 2", "Description 2", new BigDecimal("200.00"), 3, EnumProductCategory.MUSIC));
        multipleProducts.add(new ProductDTO("PRD-003", "Product 3", "Description 3", new BigDecimal("150.00"), 1, EnumProductCategory.CLOTHES));

        OrderDTO multiOrderDTO = new OrderDTO(
            1L,
            multipleProducts,
            address,
            EnumOrderStatus.PROCESSING,
            "customer@example.com",
            OffsetDateTime.now(),
            450.0,
            EnumPaymenType.DEBIT_CARD
        );

        order = new Order(multiOrderDTO);

        assertEquals(3, order.getItems().size());
        for (Product product : order.getItems()) {
            assertEquals(order, product.getOrder());
        }
    }

    @Test
    @DisplayName("Pedido com lista vazia de produtos deve ter lista vazia")
    void orderWithEmptyProductListShouldHaveEmptyList() {
        OrderDTO emptyOrderDTO = new OrderDTO(
            1L,
            new ArrayList<>(),
            address,
            EnumOrderStatus.SENT,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.PIX
        );

        order = new Order(emptyOrderDTO);

        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    @DisplayName("Pedido com lista nula de produtos deve ter lista vazia")
    void orderWithNullProductListShouldHaveEmptyList() {
        OrderDTO nullItemsOrderDTO = new OrderDTO(
            1L,
            null,
            address,
            EnumOrderStatus.DELIVERED,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.BOLETO
        );

        order = new Order(nullItemsOrderDTO);

        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    @DisplayName("Pedido deve suportar pagamento via CREDIT_CARD")
    void orderShouldSupportCreditCardPayment() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.CREDIT_CARD
        );

        order = new Order(dto);

        assertEquals(EnumPaymenType.CREDIT_CARD, order.getPaymentType());
    }

    @Test
    @DisplayName("Pedido deve suportar pagamento via DEBIT_CARD")
    void orderShouldSupportDebitCardPayment() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.DEBIT_CARD
        );

        order = new Order(dto);

        assertEquals(EnumPaymenType.DEBIT_CARD, order.getPaymentType());
    }

    @Test
    @DisplayName("Pedido deve suportar pagamento via BOLETO")
    void orderShouldSupportBoletoPayment() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.BOLETO
        );

        order = new Order(dto);

        assertEquals(EnumPaymenType.BOLETO, order.getPaymentType());
    }

    @Test
    @DisplayName("Pedido deve suportar pagamento via PIX")
    void orderShouldSupportPixPayment() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.PIX
        );

        order = new Order(dto);

        assertEquals(EnumPaymenType.PIX, order.getPaymentType());
    }

    @Test
    @DisplayName("Definir status PENDING_PAYMENT no pedido")
    void orderWithPendingPaymentStatus() {
        order = new Order(orderDTO);
        order.setStatus(EnumOrderStatus.PENDING_PAYMENT);

        assertEquals(EnumOrderStatus.PENDING_PAYMENT, order.getStatus());
    }

    @Test
    @DisplayName("Definir status PROCESSING no pedido")
    void orderWithProcessingStatus() {
        order = new Order(orderDTO);
        order.setStatus(EnumOrderStatus.PROCESSING);

        assertEquals(EnumOrderStatus.PROCESSING, order.getStatus());
    }

    @Test
    @DisplayName("Definir status SENT no pedido")
    void orderWithSentStatus() {
        order = new Order(orderDTO);
        order.setStatus(EnumOrderStatus.SENT);

        assertEquals(EnumOrderStatus.SENT, order.getStatus());
    }

    @Test
    @DisplayName("Definir status DELIVERED no pedido")
    void orderWithDeliveredStatus() {
        order = new Order(orderDTO);
        order.setStatus(EnumOrderStatus.DELIVERED);

        assertEquals(EnumOrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    @DisplayName("Pedido com endereço completo")
    void orderWithCompleteAddress() {
        order = new Order(orderDTO);

        Address orderAddress = order.getAddress();
        assertNotNull(orderAddress);
        assertEquals("Av. Paulista", orderAddress.getStreet());
        assertEquals("1282", orderAddress.getNumber());
        assertEquals("Sao Paulo", orderAddress.getCity());
        assertEquals("Santa Luzia", orderAddress.getNeighborhood());
        assertEquals("SP", orderAddress.getState());
        assertEquals("30585-345", orderAddress.getCep());
        assertEquals("Casa", orderAddress.getComplement());
    }

    @Test
    @DisplayName("Pedido com endereço sem complemento")
    void orderWithAddressWithoutComplement() {
        Address addressNoComplement = new Address(
            "Rua das Flores",
            "100",
            "Porto Alegre",
            "Centro",
            "RS",
            "90000-000",
            null
        );

        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            addressNoComplement,
            EnumOrderStatus.SENT,
            "user@example.com",
            OffsetDateTime.now(),
            100.0,
            EnumPaymenType.PIX
        );

        order = new Order(dto);

        assertNull(order.getAddress().getComplement());
    }

    @Test
    @DisplayName("Definir data de criação do pedido")
    void orderWithCreationDate() {
        order = new Order(orderDTO);
        OffsetDateTime orderDate = OffsetDateTime.parse("2026-08-16T02:38:00-03:00");
        order.setOrderDate(orderDate);

        assertEquals(orderDate, order.getOrderDate());
    }

    @Test
    @DisplayName("Pedido com preço zero não deve ser invalidado no construtor")
    void orderWithZeroPriceNotValidatedInConstructor() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            0.0,
            EnumPaymenType.CREDIT_CARD
        );

        order = new Order(dto);

        assertEquals(0.0, order.getOrderPrice());
    }

    @Test
    @DisplayName("Pedido com preço negativo não deve ser invalidado no construtor")
    void orderWithNegativePriceNotValidatedInConstructor() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            -100.0,
            EnumPaymenType.CREDIT_CARD
        );

        order = new Order(dto);

        assertEquals(-100.0, order.getOrderPrice());
    }

    @Test
    @DisplayName("Pedido com preço muito alto")
    void orderWithVeryHighPrice() {
        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            address,
            EnumOrderStatus.PENDING_PAYMENT,
            "user@example.com",
            OffsetDateTime.now(),
            999999999.99,
            EnumPaymenType.CREDIT_CARD
        );

        order = new Order(dto);

        assertEquals(999999999.99, order.getOrderPrice());
    }

    @Test
    @DisplayName("Dois pedidos com mesmo ID devem ser iguais")
    void ordersWithSameIdShouldBeEqual() {
        Order order1 = new Order(orderDTO);
        order1.setId(1L);

        Order order2 = new Order(orderDTO);
        order2.setId(1L);

        assertEquals(order1, order2);
    }

    @Test
    @DisplayName("Dois pedidos com IDs diferentes devem ser diferentes")
    void ordersWithDifferentIdsShouldNotBeEqual() {
        Order order1 = new Order(orderDTO);
        order1.setId(1L);

        Order order2 = new Order(orderDTO);
        order2.setId(2L);

        assertNotEquals(order1, order2);
    }

    @Test
    @DisplayName("Pedido com email de cliente válido")
    void orderWithValidCustomerEmail() {
        order = new Order(orderDTO);

        assertEquals("user057@icpaecommerce.com", order.getClientEmail());
    }

    @Test
    @DisplayName("Pedido sem complemento no endereço")
    void orderWithoutAddressComplement() {
        Address addressWithoutComplement = new Address(
            "Travessa Sao Joao",
            "1063",
            "Curitiba",
            "Cidade Alta",
            "PR",
            "33788-699",
            null
        );

        OrderDTO dto = new OrderDTO(
            1L,
            productDTOs,
            addressWithoutComplement,
            EnumOrderStatus.PROCESSING,
            "user@example.com",
            OffsetDateTime.now(),
            1384.08,
            EnumPaymenType.BOLETO
        );

        order = new Order(dto);

        assertNull(order.getAddress().getComplement());
        assertEquals("Travessa Sao Joao", order.getAddress().getStreet());
    }

    @Test
    @DisplayName("Modificar produtos após criação do pedido")
    void modifyProductsAfterOrderCreation() {
        order = new Order(orderDTO);
        int initialSize = order.getItems().size();

        Product newProduct = new Product(
            new ProductDTO(
                "PRD-NEW",
                "New Product",
                "New Description",
                new BigDecimal("50.00"),
                5,
                EnumProductCategory.ACCESSORIES
            )
        );
        newProduct.setOrder(order);
        order.getItems().add(newProduct);

        assertEquals(initialSize + 1, order.getItems().size());
    }
}






