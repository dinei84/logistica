package com.logistica.shipment;

import java.sql.Date;

import com.logistica.collaborator.CollaboratorModel;
import com.logistica.driver.DriverModel;
import com.logistica.order.OrderModel;
import com.logistica.vehicle.VehicleModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shipment")
public class ShipmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Long vehicleId;
    private Long driverId;
    private Long orderId;
    private Long collaboratorId;

    public ShipmentModel() {
    }
    public ShipmentModel(Long id, Date date, Long vehicleId, Long driverId, Long orderId, Long collaboratorId) {
        this.id = id;
        this.date = date;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.orderId = orderId;
        this.collaboratorId = collaboratorId;
    }

    //Relacionamentos com outras entidades
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleModel vehicle;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private DriverModel driver;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;
    @ManyToOne
    @JoinColumn(name = "collaborator_id")
    private CollaboratorModel collaborator;



    //Getters e Setters
    public VehicleModel getVehicle() {
        return vehicle;
    }
    public DriverModel getDriver() {
        return driver;
    }
    public OrderModel getOrder() {
        return order;
    }
    public CollaboratorModel getCollaborator() {
        return collaborator;
    }
    public Long getVehicleId() {
        return vehicleId;
    }
    public Long getDriverId() {
        return driverId;
    }
    public Long getOrderId() {
        return orderId;
    }
    public Long getCollaboratorId() {
        return collaboratorId;
    }
    public void setVehicle(VehicleModel vehicle) {
        this.vehicle = vehicle;
    }
    public void setDriver(DriverModel driver) {
        this.driver = driver;
    }
    public void setOrder(OrderModel order) {
        this.order = order;
    }
    public void setCollaborator(CollaboratorModel collaborator) {
        this.collaborator = collaborator;
    }
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public void setCollaboratorId(Long collaboratorId) {
        this.collaboratorId = collaboratorId;
    }
}