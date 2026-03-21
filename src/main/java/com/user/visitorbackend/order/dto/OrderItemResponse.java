package com.user.visitorbackend.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long orderedProductId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal priceFromUi,
        BigDecimal configuredPrice,
        BigDecimal lineTotal
) {
}
