package com.logistica.shipment;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.logistica.driver.DriverModel;
import com.logistica.order.OrderModel;
import com.logistica.vehicle.VehicleModel;

@Service
public class ShipmentService {

    public final ShipmentRepository repository;

    public ShipmentService(ShipmentRepository repository) {
        this.repository = repository;
    }

    //Salvar Envio
    public ShipmentDTO saveShipment(ShipmentDTO shipmentDTO){
        ShipmentModel shipment = new ShipmentModel(shipmentDTO.id(), Date.valueOf(shipmentDTO.date()), shipmentDTO.vehicleId(), shipmentDTO.driverId(), shipmentDTO.orderId(), shipmentDTO.collaboratorId());
        ShipmentModel savedShipment = repository.save(shipment);
        return toDTO(savedShipment);
    }
    //Listar todos os Envios
    public List<ShipmentDTO> getAllShipments(){
        List<ShipmentModel> shipments = repository.findAll();
        return shipments.stream()
                .map(shipment -> new ShipmentDTO(shipment.getId(), shipment.getDate(), shipment.getVehicleId(), shipment.getDriverId(), shipment.getOrderId(), shipment.getCollaboratorId()))
                .toList();
    }
    //Listar Envio por ID
    public ShipmentDTO getShipmentById(Long id){
        ShipmentModel shipment = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found"));
        return new ShipmentDTO(shipment.getId(), shipment.getDate(), shipment.getVehicleId(), shipment.getDriverId(), shipment.getOrderId(), shipment.getCollaboratorId());
    }
    //Atualizar Envio
    public ShipmentDTO updateShipment(Long id, ShipmentDTO shipmentDTO){
        ShipmentModel shipment = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found"));
        shipment.setDate(Date.valueOf(shipmentDTO.date()));
        shipment.setVehicleId(shipmentDTO.vehicleId());
        shipment.setDriverId(shipmentDTO.driverId());
        shipment.setOrderId(shipmentDTO.orderId());
        shipment.setCollaboratorId(shipmentDTO.collaboratorId());
        ShipmentModel updatedShipment = repository.save(shipment);
        return toDTO(updatedShipment);
    }
    //Deletar Envio
    public void deleteShipment(Long id){
        repository.deleteById(id);
    }
    //Deletar Envio
    public void deleteShipment(Long id) {
        repository.deleteById(id);
    }
    //Mapeadores entre DTO e Model
    private ShipmentModel toModel(ShipmentDTO dto) {
        if (dto == null) return null;
        return new ShipmentModel(dto.id(), dto.date(), dto.vehicleId(), dto.driverId(), dto.orderId(), dto.collaboratorId());
    }
    private ShipmentDTO toDTO(ShipmentModel model) {
        if (model == null) return null;
        return new ShipmentDTO(model.getId(), model.getDate(), model.getVehicleId(), model.getDriverId(), model.getOrderId(), model.getCollaboratorId());
    }
}
