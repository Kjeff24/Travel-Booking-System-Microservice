package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.mappers.OrderMapper;
import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.models.CartItem;
import com.bexos.cartservice.repositories.CartItemMongoTemplate;
import com.bexos.cartservice.repositories.CartItemRepository;
import com.bexos.cartservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final CartItemMongoTemplate cartItemMongoTemplate;


    public ResponseEntity<CartItem> addToCart(AddToCartRequest request) {
        String userId = request.userId();
        String bookingId = request.bookingId();
        Optional<Order> existingOrderOptional = orderRepository.findByUserId(userId);
        if (existingOrderOptional.isPresent()) {
            Order order = existingOrderOptional.get();
            Optional<CartItem> existingOrderItemOptional = cartItemRepository.findByBookingIdAndOrderId(bookingId, order.getId());
            return existingOrderItemOptional.map(
                    orderItem -> ResponseEntity.ok(updateCartItemQuantity(orderItem)))
                    .orElseGet(() -> ResponseEntity.ok(createNewCartItem(bookingId, order.getId())));
        } else {
            Order savedOrder = orderRepository.save(Order.builder().userId(userId).build());
            return ResponseEntity.ok(createNewCartItem(bookingId, savedOrder.getId()));
        }
    }

    public ResponseEntity<Integer> findCartItemsQuantity(String userId) {
        Optional<Order> order = orderRepository.findByUserId(userId);
        if (order.isPresent()) {
            int cartItemQuantity = cartItemMongoTemplate.getTotalQuantityByOrderId(order.get().getId());
            return ResponseEntity.ok(cartItemQuantity);
        }
        return ResponseEntity.ok(0);
    }

    public ResponseEntity<List<CartItem>> findCartItems(String userId) {
        Optional<Order> order = orderRepository.findByUserId(userId);
        if (order.isPresent()) {
            List<CartItem> cartItems = cartItemRepository.findAllByOrderId(order.get().getId());
            return ResponseEntity.ok(cartItems);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    private CartItem updateCartItemQuantity(CartItem existingCartItem) {
        existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        return cartItemRepository.save(existingCartItem);
    }

    private CartItem createNewCartItem(String bookingId, String orderId) {
        return cartItemRepository.save(orderMapper.toNewOrderItem(bookingId, 1, orderId));
    }


}
