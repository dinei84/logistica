package com.logistica.controlelog.vehicle;

import java.util.List;

import org.springframework.stereotype.Service;
import com.logistica.controlelog.exception.BadRequestException;
import com.logistica.controlelog.exception.ResourceNotFoundException;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    //Salvar Veiculo
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO){
        // Validação: Verifica se os campos obrigatórios estão presentes
        if (vehicleDTO.vehicleType() == null || vehicleDTO.vehicleType().isBlank() ||
            vehicleDTO.plate() == null || vehicleDTO.plate().isBlank()) {
            throw new BadRequestException("O Tipo do veículo e a Placa são obrigatórios.");
        }

        VehicleModel vehicle = new VehicleModel();
        vehicle.setVehicleType(vehicleDTO.vehicleType());
        vehicle.setPlate(vehicleDTO.plate());
        vehicle.setPlate2(vehicleDTO.plate2());
        vehicle.setPlate3(vehicleDTO.plate3());
        vehicle.setPlate4(vehicleDTO.plate4());
        VehicleModel savedVehicle = repository.save(vehicle);
        return new VehicleDTO(savedVehicle.getId(), savedVehicle.getVehicleType(), savedVehicle.getPlate(), savedVehicle.getPlate2(), savedVehicle.getPlate3(), savedVehicle.getPlate4());
    }

    //Listar todos os Veiculos
    public List<VehicleDTO> getAllVehicles(){
        List<VehicleModel> vehicles = repository.findAll();
        return vehicles.stream()
                .map(vehicle -> new VehicleDTO(vehicle.getId(), vehicle.getVehicleType(), vehicle.getPlate(), vehicle.getPlate2(), vehicle.getPlate3(), vehicle.getPlate4()))
                .toList();
    }

    //Listar Veiculos por ID
    public VehicleDTO getVehicleId(Long id){
        VehicleModel vehicle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
        return new VehicleDTO(vehicle.getId(), vehicle.getVehicleType(), vehicle.getPlate(), vehicle.getPlate2(), vehicle.getPlate3(), vehicle.getPlate4());
    }

    //Atualizar Veiculo
    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO){
        VehicleModel vehicle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        if (vehicleDTO.vehicleType() == null || vehicleDTO.vehicleType().isBlank() ||
                vehicleDTO.plate() == null || vehicleDTO.plate().isBlank()) {
            throw new BadRequestException("O Tipo do veículo e a Placa são obrigatórios.");
        }

        vehicle.setVehicleType(vehicleDTO.vehicleType());
        vehicle.setPlate(vehicleDTO.plate());
        vehicle.setPlate2(vehicleDTO.plate2());
        vehicle.setPlate3(vehicleDTO.plate3());
        vehicle.setPlate4(vehicleDTO.plate4());
        VehicleModel updateVehicle = repository.save(vehicle);
        return new VehicleDTO(updateVehicle.getId(), updateVehicle.getVehicleType(), updateVehicle.getPlate(), updateVehicle.getPlate2(), updateVehicle.getPlate3(), updateVehicle.getPlate4());
    }

    //Deletar Veiculo
    public void deleteVehicle(Long id){
        repository.deleteById(id);
    }


    



}





