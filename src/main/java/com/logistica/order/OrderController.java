package com.logistica.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // Endpoint de listar todas as ordens
    @GetMapping
    public List<OrderDTO> getAllOrders(){
        List<OrderModel> oreder = service.getAllOrders();
        return oreder.stream()
                .map(orderModel -> new OrderDTO(
                        orderModel.getId(),
                        orderModel.getOrderNumber(),
                        orderModel.getProduct(),
                        orderModel.getPackaging()
                ))
                .toList();
    }

    // Endpoint de buscar ordem por ID
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id){
        return service.getOrderById(id);
    }

    //Endpoint de criar nova ordem
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        OrderDTO createdOrder = service.saveOrder(orderDTO);
        return ResponseEntity.created(URI.create("/orders/" + createdOrder.id())).body(createdOrder);
    }

    //Endpoint de atualizar ordem existente
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        OrderDTO updatedOrder = service.updateOrder(id, orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    //Endpoint de deletar ordem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
