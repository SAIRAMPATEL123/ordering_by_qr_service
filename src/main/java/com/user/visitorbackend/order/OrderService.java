package com.user.visitorbackend.order;

import com.user.visitorbackend.common.BusinessValidationException;
import com.user.visitorbackend.common.ResourceNotFoundException;
import com.user.visitorbackend.order.dto.CreateOrderRequest;
import com.user.visitorbackend.order.dto.OrderItemRequest;
import com.user.visitorbackend.order.dto.OrderItemResponse;
import com.user.visitorbackend.order.dto.OrderResponse;
import com.user.visitorbackend.product.Product;
import com.user.visitorbackend.product.ProductRepository;
import com.user.visitorbackend.user.User;
import com.user.visitorbackend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        User customer = userRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for id: " + request.customerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.CREATED);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findByProductNameIgnoreCase(itemRequest.productName())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found for name: " + itemRequest.productName()
                    ));

            if (Boolean.FALSE.equals(product.getActive())) {
                throw new BusinessValidationException("Product is inactive: " + product.getProductName());
            }

            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(product);
            orderedProduct.setProductNameSnapshot(product.getProductName());
            orderedProduct.setQuantity(itemRequest.quantity());
            orderedProduct.setPriceFromUi(itemRequest.priceFromUi());
            orderedProduct.setConfiguredPrice(product.getConfiguredPrice());

            BigDecimal lineTotal = itemRequest.priceFromUi()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            orderedProduct.setLineTotal(lineTotal);

            order.addItem(orderedProduct);
            totalAmount = totalAmount.add(lineTotal);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getPastOrdersByCustomerId(Long customerId) {
        userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for id: " + customerId));

        return orderRepository.findByCustomerIdOrderByOrderedAtDesc(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProductNameSnapshot(),
                        item.getQuantity(),
                        item.getPriceFromUi(),
                        item.getConfiguredPrice(),
                        item.getLineTotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getOrderedAt(),
                items
        );
    }

    private String generateOrderNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        long uniquePart = System.currentTimeMillis() % 1_000_000;
        return "ORD-" + datePart + "-" + String.format("%06d", uniquePart);
    }
}
