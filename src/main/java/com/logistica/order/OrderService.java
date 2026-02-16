package com.logistica.order;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    public final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // Salvar Ordem
    public OrderDTO saveOrder(OrderDTO orderDTO){
        OrderModel order = new OrderModel(orderDTO.product(), orderDTO.packaging(), orderDTO.recipient());
        order.setProduct(orderDTO.product());
        order.setPackaging(orderDTO.packaging());
        order.setRecipient(orderDTO.recipient());
        OrderModel savedOrder = repository.save(order);
        return new OrderDTO(savedOrder.getId(), savedOrder.getOrderNumber(), savedOrder.getProduct(), savedOrder.getPackaging(), savedOrder.getRecipient());
    }

    //Listar todas as Ordens
    public List<OrderModel> getAllOrders(){
        return repository.findAll();
    }

    // Buscar Ordem por ID
    public OrderDTO getOrderById(Long id){
        return repository.findById(id)
                .map(orderModel -> new OrderDTO(
                        orderModel.getId(),
                        orderModel.getOrderNumber(),
                        orderModel.getProduct(),
                        orderModel.getPackaging(),
                        orderModel.getRecipient()
                ))
                .orElse(null);
    }

    //Atualizar Ordem
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO){
        OrderModel order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Apenas atualiza o número do pedido se um novo valor for fornecido
        if (orderDTO.orderNumber() != null) {
            order.setOrderNumber(orderDTO.orderNumber());
        }
        order.setProduct(orderDTO.product());
        order.setPackaging(orderDTO.packaging());
        order.setRecipient(orderDTO.recipient());
        OrderModel updatedOrder = repository.save(order);
        return new OrderDTO(updatedOrder.getId(), updatedOrder.getOrderNumber(), updatedOrder.getProduct(), updatedOrder.getPackaging(), updatedOrder.getRecipient());
    }

    // Deletar Ordem
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
