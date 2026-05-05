package com.logistica.controlelog.client;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "clients")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
}

