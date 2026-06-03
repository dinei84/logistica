package com.logistica.controlelog.shipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShipmentModel> createShipment(@RequestBody ShipmentDTO dto) {
        ShipmentModel shipment = service.createShipment(dto);
        return ResponseEntity.ok(shipment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentModel> update(@PathVariable Long id, @RequestBody ShipmentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        service.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }


    //Pageable para procura geral
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Page<ShipmentModel>> getAllShipments(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
            ){
        Page<ShipmentModel> shipmentPage = service.findAll(pageable);
        return ResponseEntity.ok(shipmentPage);
    }

}
