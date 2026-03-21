package com.user.visitorbackend.order.dto;

import com.user.visitorbackend.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String orderNumber,
        Long customerId,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime orderedAt,
        List<OrderItemResponse> items
) {
}
