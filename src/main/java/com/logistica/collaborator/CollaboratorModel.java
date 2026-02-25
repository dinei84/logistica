package com.logistica.collaborator;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "collaborator")
public class CollaboratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public CollaboratorModel() {
    }

    public CollaboratorModel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
