package com.logistica.controlelog.order;

public record OrderDTO(
    Long id,
    String orderNumber,
    String product,
    String packaging,
    String recipient
) {
}

