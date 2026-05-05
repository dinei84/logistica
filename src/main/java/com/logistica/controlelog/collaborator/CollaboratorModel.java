package com.logistica.controlelog.collaborator;

import com.logistica.auth.UserModel;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel users;

    public CollaboratorModel() {
    }

    public CollaboratorModel(Long id, String nome, UserModel user) {
        this.id = id;
        this.nome = nome;
        this.users = user;
    }

}
