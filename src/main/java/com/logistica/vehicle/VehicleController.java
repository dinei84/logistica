package com.logistica.vehicle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    public final VehicleService service;

    public VehicleController(VehicleService service){
        this.service = service;
    }

    //Endpoint para listar todos os veiculos
    @GetMapping
    public List<VehicleDTO> getAllVehicles(){
        return service.getAllVehicles();
    }

    //Endpoint para listar Veiculo por ID
    @GetMapping("/{id}")
    public VehicleDTO getVehicleById(@PathVariable Long id){
        return service.getVehicleId(id);
    }

    //Endpoint para criar novo Veiculo
    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleDTO vehicleDTO){
        VehicleDTO savedVehicle = service.saveVehicle(vehicleDTO);
        return ResponseEntity.created(URI.create("/vehicles/" + savedVehicle.id())).body(savedVehicle);
    }

    //Endpoint para atualizar Veiculo existente
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO){
        VehicleDTO updatedVehicle = service.updateVehicle(id, vehicleDTO);
        return ResponseEntity.ok(updatedVehicle);
    }

    //Endpoint para deletar Veiculo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        service.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
