package com.bexos.cartservice.services;

import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public ResponseEntity<Order> createOrder(Order order) {
        return null;
    }

    public ResponseEntity<List<Order>> getAllOrders() {
         List<Order> orders = orderRepository.findAll();
         return ResponseEntity.ok(orders);
    }

    public ResponseEntity<Order> getOrderById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    public void updateOrder(Order Updatedorder) {
        orderRepository.save(Updatedorder);
    }

    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
