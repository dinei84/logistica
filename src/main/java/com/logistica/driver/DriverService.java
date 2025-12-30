package com.logistica.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository repository;

    public DriverService(DriverRepository repository){
        this.repository = repository;
    }


    //Listar todos os motoristas
    public List<DriverDTO> getAllDrivers(){
        List<DriverModel> drivers = repository.findAll();
        return drivers.stream()
                .map(driver -> new DriverDTO(driver.getId(), driver.getName(), driver.getCpf(), driver.getPhone()))
                .toList();
    }

    //Listar motorista por id
    public DriverDTO getDriverById(Long id){
        DriverModel driver = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        return new DriverDTO(driver.getId(), driver.getName(), driver.getCpf(), driver.getPhone());
    }

    //Salvar novo motorista
    public DriverDTO createDriver(DriverDTO driverDTO){
        DriverModel driver = new DriverModel();
        driver.setName(driverDTO.name());
        driver.setPhone(driverDTO.phone());
        driver.setCpf(driverDTO.cpf());
        DriverModel savedDriver = repository.save(driver);
        return new DriverDTO(savedDriver.getId(), savedDriver.getName(), savedDriver.getCpf(), savedDriver.getPhone());
    }

    //Atualizar motorista existente
    public DriverDTO updateDriver(Long id, DriverDTO driverDTO){
        DriverModel driver = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setName(driverDTO.name());
        driver.setPhone(driverDTO.phone());
        driver.setCpf(driverDTO.cpf());
        DriverModel updatedDriver = repository.save(driver);
        return new DriverDTO(updatedDriver.getId(), updatedDriver.getName(), updatedDriver.getCpf(), updatedDriver.getPhone());
    }

    //Deletar motorista
    public void deleteDriver(Long id) {
        repository.deleteById(id);
    }
}
