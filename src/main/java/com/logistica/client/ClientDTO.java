package com.logistica.client;

public record ClientDTO(
    Long id,
    String name,
    String email,
    String phone
) {
}

