package com.logistica.shipment;

import com.logistica.collaborator.CollaboratorModel;
import com.logistica.collaborator.CollaboratorRepository;
import com.logistica.driver.DriverRepository;
import com.logistica.order.OrderRepository;
import com.logistica.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        // Metodo para atualizar tudo de uma vez
        @Transactional
        public ShipmentModel update(Long id, ShipmentDTO dto) {
                ShipmentModel shipment = shipmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada"));

                // Atualiza a data se houver no DTO
                if (dto.date() != null) {
                        shipment.setDate(dto.date());
                }

                // Atualiza as assossiações
                if (dto.vehicleId() != null) {
                        VehicleModel vehicle = vehicleRepository.findById(dto.vehicleId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
                        shipment.setVehicle(vehicle);
                }

                if (dto.driverId() != null) {
                        DriverModel driver = driverRepository.findById(dto.driverId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
                        shipment.setDriver(driver);
                }

                if (dto.orderId() != null) {
                        OrderModel order = orderRepository.findById(dto.orderId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
                        shipment.setOrder(order);
                }

                if (dto.collaboratorId() != null) {
                        CollaboratorModel collaborator = collaboratorRepository.findById(dto.collaboratorId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
                        shipment.setCollaborator(collaborator);
                }
                return shipmentRepository.save(shipment);
        }

        // Deleter um Shipment
        public void deleteShipment(Long id) {
                if (!shipmentRepository.existsById(id)) {
                        throw new ResourceNotFoundException("Entrega não encontrada");
                }
                shipmentRepository.deleteById(id);
        }

        public Page<ShipmentModel> findAll(Pageable pageable) {
                return shipmentRepository.findAll(pageable);
        }
}
