package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.order.EnumOrderStatus;
import com.api.ICPAEcommerce.domain.order.Order;
import com.api.ICPAEcommerce.domain.order.OrderMapper;
import com.api.ICPAEcommerce.domain.orderItem.OrderItem;
import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.dto.order.OrderDetailDTO;
import com.api.ICPAEcommerce.dto.order.OrderRequestDTO;
import com.api.ICPAEcommerce.repositories.OrderRepository;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository; // Injetado para validar estoque e preço
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderDetailDTO>> listOrders() {
        var orders = orderRepository.findAll().stream()
                .map(orderMapper::toDetailDTO)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @Transactional(readOnly = true)
    public ResponseEntity detailOrder(Long id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(orderMapper.toDetailDTO(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity saveOrder(OrderRequestDTO orderDTO) {
        Order order = new Order();
        order.setClientEmail(orderDTO.clientEmail());
        order.setAddress(orderDTO.address());
        order.setPaymentType(orderDTO.paymentType());
        order.setOrderDate(OffsetDateTime.now());
        order.setStatus(EnumOrderStatus.PENDING_PAYMENT); // Status padrão[cite: 17]

        List<OrderItem> items = new ArrayList<>();
        double totalPrice = 0.0;

        for (var itemDTO : orderDTO.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemDTO.productId()));

            // Proteção de Estoque
            if (product.getQuantity() < itemDTO.quantity() || !product.getActive()) {
                return ResponseEntity.badRequest().body("Estoque insuficiente ou produto inativo para o item: " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity());
            item.setPriceAtTimeOfPurchase(product.getPrice()); // Trava o preço histórico!

            // Cálculo Seguro (Backend)
            totalPrice += product.getPrice().doubleValue() * itemDTO.quantity();

            // Baixa no Estoque
            product.setQuantity(product.getQuantity() - itemDTO.quantity());

            items.add(item);
        }

        order.setItems(items);
        order.setOrderPrice(totalPrice); // Substitui a falha de segurança do DTO antigo[cite: 19]

        orderRepository.save(order);
        return ResponseEntity.ok(orderMapper.toDetailDTO(order));
    }

    @Transactional
    public ResponseEntity updateOrderStatus(Long id, EnumOrderStatus newStatus) {
        var optional = orderRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido inexistente!");
        }

        Order order = optional.get();

        // BLINDAGEM: Evita o bug do "Duplo Cancelamento"
        // Só devolvemos o estoque se o novo status for CANCELED e o status atual NÃO for CANCELED
        if (newStatus == EnumOrderStatus.CANCELED && order.getStatus() != EnumOrderStatus.CANCELED) {

            // Itera sobre todos os itens que foram comprados neste pedido
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();

                // Matemática de Reversão: Estoque Atual + Quantidade que havia sido comprada
                int refundedStock = product.getQuantity() + item.getQuantity();

                // Devolve ao produto
                product.setQuantity(refundedStock);

                // Opcional: Se quiser registrar um log da operação
                // System.out.println("Estoque do produto " + product.getName() + " revertido para: " + refundedStock);
            }
        }

        // Se por um acaso o pedido estava CANCELED e o lojista reativou para PROCESSING,
        // seria necessário refazer a lógica inversa (tirar do estoque de novo),
        // mas em e-commerces reais, um pedido cancelado é irreversível (Terminal State).

        order.setStatus(newStatus);

        return ResponseEntity.ok(orderMapper.toDetailDTO(order));
    }
}