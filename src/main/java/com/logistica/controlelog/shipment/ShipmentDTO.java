package com.logistica.controlelog.shipment;

import java.time.LocalDate;

public record ShipmentDTO(
                Long id,
                LocalDate date,
                Long vehicleId,
                Long driverId,
                Long orderId,
                Long collaboratorId) {
}
