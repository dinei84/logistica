package com.logistica.shipment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShipmentModel> createShipment(@RequestBody ShipmentDTO dto) {
        ShipmentModel shipment = service.createShipment(dto);
        return ResponseEntity.ok(shipment);
    }

    @PutMapping("{id}/vehicle")
    public ResponseEntity<ShipmentModel> updateVehicle(
            @PathVariable Long id,
            @RequestParam Long vehicleId) {
        ShipmentModel shipment = service.updateVehicle(id, vehicleId);
        return ResponseEntity.ok(shipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        service.removeVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
