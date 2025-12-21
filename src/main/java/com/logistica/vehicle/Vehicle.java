package com.logistica.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "plate")
    private String plate;

    @Column(name = "plate2")
    private String plate2;

    @Column(name = "plate3")
    private String plate3;

    @Column(name = "plate4")
    private String plate4;
}

