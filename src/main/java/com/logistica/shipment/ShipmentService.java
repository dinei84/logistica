package com.logistica.shipment;

import com.logistica.collaborator.CollaboratorModel;
import com.logistica.collaborator.CollaboratorRepository;
import com.logistica.driver.DriverRepository;
import com.logistica.order.OrderRepository;
import com.logistica.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.logistica.driver.DriverModel;
import com.logistica.order.OrderModel;
import com.logistica.vehicle.VehicleModel;

import java.sql.Date;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final OrderRepository orderRepository;
    private final CollaboratorRepository collaboratorRepository;



    public ShipmentService(
            ShipmentRepository shipmentRepository,
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            OrderRepository orderRepository,
            CollaboratorRepository collaboratorRepository
    ) {
        this.shipmentRepository = shipmentRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.orderRepository = orderRepository;
        this.collaboratorRepository = collaboratorRepository;
    }

    @Transactional
    public ShipmentModel createShipment(ShipmentDTO shipmentDTO){
        try{
            ShipmentModel shipment = new ShipmentModel();
            shipment.setDate((Date) shipmentDTO.date());

            //Buscar e associar entidades existentes
            VehicleModel vehicle = vehicleRepository.findById(shipmentDTO.vehicleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
            shipment.setVehicle(vehicle);

            DriverModel driver = driverRepository.findById(shipmentDTO.driverId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motorista não encontrado"));
            shipment.setDriver(driver);

            OrderModel order = orderRepository.findById(shipmentDTO.orderId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
            shipment.setOrder(order);

            CollaboratorModel collaborator = collaboratorRepository.findById(shipmentDTO.collaboratorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado"));
            shipment.setCollaborator(collaborator);

            return shipmentRepository.save(shipment);

        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
