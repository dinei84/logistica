package com.logistica.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number") //numero da ordem
    private String orderNumber;

    @Column(name = "product") //produto
    private String product;

    @Column(name = "packaging") // embalagem
    private String packaging;

    @Column(name= "recipient") //recebedor
    private String recipient;

    public OrderModel() {
    }

    public OrderModel(String product, String packaging, String recipient){
        this.orderNumber = UUID.randomUUID().toString();
        this.product = product;
        this.packaging = packaging;
        this.recipient = recipient;
    }


}
