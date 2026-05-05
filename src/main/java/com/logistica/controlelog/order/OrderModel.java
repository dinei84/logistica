package com.logistica.controlelog.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number") //numero do pedido
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
