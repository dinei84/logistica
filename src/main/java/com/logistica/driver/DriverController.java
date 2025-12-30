package com.logistica.driver;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService service;

    public DriverController(DriverService service){
        this.service = service;
    }

    //Endpoint para listar todos os motoristas
    @GetMapping
    public List<DriverDTO> getAllDrivers(){
        return service.getAllDrivers();
    }

    //Endpoint para listar motorista por Id
    @GetMapping("/{id}")
    public DriverDTO getDriverById(@PathVariable Long id){
        return service.getDriverById(id);
    }

    //Endpoint para criar novo motorista
    @PostMapping
    public ResponseEntity<DriverDTO> createDriver(@RequestBody DriverDTO driverDTO){
        DriverDTO createdDriver = service.createDriver(driverDTO);
        return ResponseEntity.created(URI.create("/drivers/" + createdDriver.id())).body(createdDriver);
    }

    //Endpoint para atualizar motorista existente
    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id, @RequestBody DriverDTO driverDTO){
        DriverDTO updatedDriver = service.updateDriver(id, driverDTO);
        return ResponseEntity.ok(updatedDriver);
    }

    //Endpoint para deletar motorista
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id){
        service.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

}
