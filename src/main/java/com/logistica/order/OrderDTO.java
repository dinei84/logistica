package com.logistica.order;

public record OrderDTO(
    Long id,
    String orderNumber,
    String product,
    String packaging
) {
}

