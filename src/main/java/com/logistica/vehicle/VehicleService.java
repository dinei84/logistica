package com.logistica.vehicle;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    //Listar todos os Veiculos
    public List<VehicleDTO> getAllVehicles(){
        List<VehicleModel> vehicles = repository.findAll();
        return vehicles.stream()
                .map(vehicle -> new VehicleDTO(vehicle.getId(), vehicle.getVehicleType(), vehicle.getPlate(), vehicle.getPlate2(), vehicle.getPlate3(), vehicle.getPlate4()))
                .toList();
    }

    



}