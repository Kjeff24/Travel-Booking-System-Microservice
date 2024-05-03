package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.feign.BookingClient;
import com.bexos.cartservice.mappers.OrderMapper;
import com.bexos.cartservice.models.CartItem;
import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.repositories.CartItemMongoTemplate;
import com.bexos.cartservice.repositories.CartItemRepository;
import com.bexos.cartservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final BookingClient bookingClient;


    public ResponseEntity<CartItem> addToCart(AddToCartRequest request) {
        String userId = request.userId();
        String bookingId = request.bookingId();
        Optional<Order> existingOrderOptional = orderRepository.findByUserId(userId);
        if (existingOrderOptional.isPresent()) {
            Order order = existingOrderOptional.get();
            Optional<CartItem> existingOrderItemOptional = cartItemRepository.findByBookingIdAndOrderId(bookingId, order.getId());
            return existingOrderItemOptional.map(
                            orderItem -> ResponseEntity.ok(updateCartItem(orderItem, request.price(), "add")))
                    .orElseGet(() -> ResponseEntity.ok(createNewCartItem(bookingId, order.getId(), request.price())));
        } else {
            Order savedOrder = orderRepository.save(Order.builder().userId(userId).build());
            return ResponseEntity.ok(createNewCartItem(bookingId, savedOrder.getId(), request.price()));
        }
    }

//    public ResponseEntity<Void> removeFromCart(AddToCartRequest request) {
//        String userId = request.userId();
//        String bookingId = request.bookingId();
//        Optional<Order> existingOrderOptional = orderRepository.findByUserId(userId);
//        if (existingOrderOptional.isPresent()) {
//            Order order = existingOrderOptional.get();
//            cartItemRepository.deleteByBookingIdAndOrderId(bookingId, order.getId());
//        }
//        return null;
//    }

    public ResponseEntity<Void> deleteFromCart(String bookingId, String userId) {
        orderRepository.findByUserId(userId).ifPresent(order ->
                cartItemRepository.deleteByBookingIdAndOrderId(bookingId, order.getId()));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<CartItem> decreaseCartItem(AddToCartRequest request) {String userId = request.userId();
        String bookingId = request.bookingId();
        Optional<Order> existingOrderOptional = orderRepository.findByUserId(userId);
        if (existingOrderOptional.isPresent()) {
            Order order = existingOrderOptional.get();
            Optional<CartItem> existingOrderItemOptional = cartItemRepository.findByBookingIdAndOrderId(bookingId, order.getId());
            if (existingOrderItemOptional.isPresent()) {
                CartItem cartItem = existingOrderItemOptional.get();
                return ResponseEntity.ok(updateCartItem(cartItem, request.price(), "decrease"));
            }
        }
        return null;
    }

    public ResponseEntity<Integer> findCartItemsQuantity(String userId) {
        Optional<Order> order = orderRepository.findByUserId(userId);
        if (order.isPresent()) {
            int cartItemQuantity = cartItemMongoTemplate.getTotalQuantityByOrderId(order.get().getId());
            return ResponseEntity.ok(cartItemQuantity);
        }
        return ResponseEntity.ok(0);
    }

    public ResponseEntity<Integer> findCartItemsTotalPrice(String userId) {
        Optional<Order> order = orderRepository.findByUserId(userId);
        if (order.isPresent()) {
            int cartItemTotalPrice = cartItemMongoTemplate.getTotalPriceByOrderId(order.get().getId());
            return ResponseEntity.ok(cartItemTotalPrice);
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


    private CartItem updateCartItem(CartItem existingCartItem, int price, String action) {
        if ("add".equals(action)) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            existingCartItem.setTotalPrice(existingCartItem.getTotalPrice() + price);
        } else if ("decrease".equals(action) && existingCartItem.getQuantity() > 1) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() - 1);
            existingCartItem.setTotalPrice(existingCartItem.getTotalPrice() - price);
        } else if ("decrease".equals(action) && existingCartItem.getQuantity() == 1) {
            cartItemRepository.delete(existingCartItem);
            return null;
        }
        return cartItemRepository.save(existingCartItem);
    }


    private CartItem createNewCartItem(String bookingId, String orderId, int price) {
        return cartItemRepository.save(orderMapper.toNewOrderItem(bookingId, 1, orderId, price));
    }


}
