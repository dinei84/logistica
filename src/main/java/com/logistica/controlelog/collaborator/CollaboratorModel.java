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

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel user;

    public CollaboratorModel() {
    }

    public CollaboratorModel(Long id, String name, UserModel user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

}
