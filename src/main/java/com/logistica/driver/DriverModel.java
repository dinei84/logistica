package com.logistica.driver;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="driver")
public class DriverModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "cpf")
    private String cpf;
}

