package com.logistica.recipient;

public record RecipientDTO(
    Long id,
    String name,
    String receiverName,
    String phone
) {
}

