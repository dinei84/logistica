package com.logistica.shipment;

import java.util.Date;

public record ShipmentDTO(
        Long id,
        Date data,
        Long vehicleId,
        Long driverId,
        Long orderId,
        Long collaboratorId
) {
}
