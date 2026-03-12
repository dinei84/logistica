package com.logistica.administrator;

import com.logistica.auth.UserModel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "administrator")
public class AdministratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel user;

    public AdministratorModel() {
    }

    public AdministratorModel(Long id, String nome, UserModel user) {
        this.id = id;
        this.nome = nome;
        this.user = user;
    }
}
