package com.logistica.controlelog.freight;


import java.math.BigDecimal;

public record FreightDTO(
        Long id,
        String infoBasic,
        BigDecimal quantity,
        BigDecimal value,
        String infoAdditional,
        Long clientId,
        Long shipmentId
) {}
