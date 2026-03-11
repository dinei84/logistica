package com.logistica.freight;

import java.math.BigDecimal;

import com.logistica.client.ClientModel;
import com.logistica.shipment.ShipmentModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "freight")
public class FreightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "info_basic")
    private String infoBasic;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "freight_value")
    private BigDecimal value;

    @Column(name = "info_additional")
    private String infoAdditional;


    // Relacionamentos com outras entidades
    // As colunas FK (client_id, shipment_id) são gerenciadas pelo @JoinColumn
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientModel client;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private ShipmentModel shipment;

    public FreightModel() {
    }

    public FreightModel(Long id, String infoBasic, BigDecimal quantity, BigDecimal value,
            String infoAdditional, ClientModel client, ShipmentModel shipment) {
        this.id = id;
        this.infoBasic = infoBasic;
        this.quantity = quantity;
        this.value = value;
        this.infoAdditional = infoAdditional;
        this.client = client;
        this.shipment = shipment;
    }
}
