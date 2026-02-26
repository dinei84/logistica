package com.logistica.shipment;

import com.logistica.collaborator.CollaboratorModel;
import com.logistica.collaborator.CollaboratorRepository;
import com.logistica.driver.DriverRepository;
import com.logistica.order.OrderRepository;
import com.logistica.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.logistica.exception.BadRequestException;
import com.logistica.exception.ResourceNotFoundException;

import com.logistica.driver.DriverModel;
import com.logistica.order.OrderModel;
import com.logistica.vehicle.VehicleModel;

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
                        CollaboratorRepository collaboratorRepository) {
                this.shipmentRepository = shipmentRepository;
                this.vehicleRepository = vehicleRepository;
                this.driverRepository = driverRepository;
                this.orderRepository = orderRepository;
                this.collaboratorRepository = collaboratorRepository;
        }

        @Transactional
        public ShipmentModel createShipment(ShipmentDTO shipmentDTO) {
                try {
                        ShipmentModel shipment = new ShipmentModel();
                        shipment.setDate(shipmentDTO.date());

                        // Buscar e associar entidades existentes
                        VehicleModel vehicle = vehicleRepository.findById(shipmentDTO.vehicleId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
                        shipment.setVehicle(vehicle);

                        DriverModel driver = driverRepository.findById(shipmentDTO.driverId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
                        shipment.setDriver(driver);

                        OrderModel order = orderRepository.findById(shipmentDTO.orderId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
                        shipment.setOrder(order);

                        CollaboratorModel collaborator = collaboratorRepository.findById(shipmentDTO.collaboratorId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
                        shipment.setCollaborator(collaborator);

                        return shipmentRepository.save(shipment);

                } catch (ResourceNotFoundException e) {
                        throw e;
                } catch (Exception e) {
                        throw new BadRequestException(e.getMessage());
                }
        }

        @Transactional
        public ShipmentModel updateVehicle(Long shipmenId, Long vehicleId) {
                ShipmentModel shipment = shipmentRepository.findById(shipmenId)
                                .orElseThrow(() -> new ResourceNotFoundException("Carregamento não encontrado"));

                VehicleModel vehicle = vehicleRepository.findById(vehicleId)
                                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

                shipment.setVehicle(vehicle);
                return shipmentRepository.save(shipment);
        }

        @Transactional
        public ShipmentModel updateDriver(Long shipmentId, Long driverId) {
                ShipmentModel shipment = shipmentRepository.findById(shipmentId)
                                .orElseThrow(() -> new ResourceNotFoundException("Carregamento não encontrado"));

                DriverModel driver = driverRepository.findById(driverId)
                                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));

                shipment.setDriver(driver);
                return shipmentRepository.save(shipment);
        }

        @Transactional
        public ShipmentModel updateVehicle(Long shipmentId) {
                ShipmentModel shipment = shipmentRepository.findById(shipmentId)
                                .orElseThrow(() -> new ResourceNotFoundException("Carregamento não encontrado"));

                shipment.setVehicle(null);
                return shipmentRepository.save(shipment);
        }

        @Transactional
        public void removeVehicle(Long shipmentId) {
                ShipmentModel shipment = shipmentRepository.findById(shipmentId)
                                .orElseThrow(() -> new ResourceNotFoundException("Carregamento não encontrado"));

                shipment.setVehicle(null);
                shipmentRepository.save(shipment);
        }

        // Metodo generico para atualizar tudo de uma vez
        @Transactional
        public ShipmentModel updateShipmentResourses(Long shipmentID,
                        Long vehicleId,
                        Long driverId,
                        Long orderId,
                        Long collaboratorId) {
                ShipmentModel shipment = shipmentRepository.findById(shipmentID)
                                .orElseThrow(() -> new ResourceNotFoundException("Carregamento não encontrado"));

                if (vehicleId != null) {
                        VehicleModel vehicle = vehicleRepository.findById(vehicleId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
                        shipment.setVehicle(vehicle);
                }

                if (driverId != null) {
                        DriverModel driver = driverRepository.findById(driverId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
                        shipment.setDriver(driver);
                }

                if (orderId != null) {
                        OrderModel order = orderRepository.findById(orderId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
                        shipment.setOrder(order);
                }

                if (collaboratorId != null) {
                        CollaboratorModel collaborator = collaboratorRepository.findById(collaboratorId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
                        shipment.setCollaborator(collaborator);
                }

                return shipmentRepository.save(shipment);
        }
}
