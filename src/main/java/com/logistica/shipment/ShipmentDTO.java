package com.logistica.shipment;

import java.util.Date;

public record ShipmentDTO(
        Long id,
        Date date,
        Long vehicleId,
        Long driverId,
        Long orderId,
        Long collaboratorId
) {
}
