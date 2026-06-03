package com.logistica.controlelog.freight;

import com.logistica.controlelog.client.ClientRepository;
import com.logistica.controlelog.exception.BadRequestException;
import com.logistica.controlelog.exception.ResourceNotFoundException;
import com.logistica.controlelog.shipment.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FreightService {

    private final FreightRepository freightRepository;
    private final ClientRepository clientRepository;
    private final ShipmentRepository shipmentRepository;

    public FreightService(FreightRepository freightRepository,
                          ClientRepository clientRepository,
                          ShipmentRepository shipmentRepository) {
        this.freightRepository = freightRepository;
        this.clientRepository = clientRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public FreightModel createFreight(FreightDTO dto) {
        try {
            FreightModel freight = new FreightModel();
            updateFields(freight, dto);
            return freightRepository.save(freight);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Transactional
    public FreightModel update(Long id, FreightDTO dto) {
        FreightModel freight = freightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Frete não encontrado"));

        updateFields(freight, dto);
        return freightRepository.save(freight);
    }

    public void deleteFreight(Long id) {
        if (!freightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Frete não encontrado");
        }
        freightRepository.deleteById(id);
    }

    public Page<FreightModel> findAll(Pageable pageable) {
        return freightRepository.findAll(pageable);
    }

    private void updateFields(FreightModel freight, FreightDTO dto) {
        if (dto.infoBasic() != null) freight.setInfoBasic(dto.infoBasic());
        if (dto.quantity() != null) freight.setQuantity(dto.quantity());
        if (dto.value() != null) freight.setValue(dto.value());
        if (dto.infoAdditional() != null) freight.setInfoAdditional(dto.infoAdditional());

        if (dto.clientId() != null) {
            freight.setClient(clientRepository.findById(dto.clientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.clientId())));
        }

        if (dto.shipmentId() != null) {
            freight.setShipment(shipmentRepository.findById(dto.shipmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Remessa não encontrada com id: " + dto.shipmentId())));
        }
    }
}
