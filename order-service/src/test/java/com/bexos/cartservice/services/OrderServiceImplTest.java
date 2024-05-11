package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddAllToCartRequest;
import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.mappers.OrderMapper;
import com.bexos.cartservice.models.CartItem;
import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.repositories.CartItemMongoTemplate;
import com.bexos.cartservice.repositories.CartItemRepository;
import com.bexos.cartservice.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private CartItemMongoTemplate cartItemMongoTemplate;
    @InjectMocks
    private OrderServiceImpl orderService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCart_ExistingOrderAndCartItem() {
        // Mocking
        String userId = "user123";
        String bookingId = "booking123";
        AddToCartRequest request = new AddToCartRequest(userId, bookingId, 100);
        Order existingOrder = Order.builder().id("order123").build();
        CartItem existingCartItem = CartItem.builder().build();
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));
        when(cartItemRepository.findByBookingIdAndOrderId(bookingId, existingOrder.getId())).thenReturn(Optional.of(existingCartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(existingCartItem);

        ResponseEntity<CartItem> responseEntity = orderService.addToCart(request);

        assertNotNull(responseEntity.getBody());
        assertEquals(existingCartItem, responseEntity.getBody());
    }

    @Test
    void testAddToCart_NewOrderAndCartItem() {
        String userId = "user123";
        String bookingId = "booking123";
        AddToCartRequest request = new AddToCartRequest(userId, bookingId, 100);
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(CartItem.builder().build());
        when(orderMapper.toNewOrderItem(bookingId, 1, "orderId", 100)).thenReturn(CartItem.builder().build());

        // Test
        ResponseEntity<CartItem> responseEntity = orderService.addToCart(request);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void addAllCartItems() {
        String userId = "user123";
        List<AddAllToCartRequest> request = new ArrayList<>();
        request.add(new AddAllToCartRequest("booking1", 2, 200));
        request.add(new AddAllToCartRequest("booking2", 3, 300));
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(CartItem.builder().build());

        ResponseEntity<List<CartItem>> responseEntity = orderService.addAllCartItems(request, userId);

        assertEquals(request.size(), Objects.requireNonNull(responseEntity.getBody()).size());
        verify(cartItemRepository, times(request.size())).save(any(CartItem.class));

    }

    @Test
    void findOrderById_OrderExists() {
        String orderId = "order123";
        Order existingOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        ResponseEntity<?> responseEntity = orderService.findOrderById(orderId);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(existingOrder, responseEntity.getBody());
    }

    @Test
    void findOrderById_OrderDoesNotExist() {
        String orderId = "order123";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = orderService.findOrderById(orderId);

        assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    void deleteAllCartItems_OrderExists() {
        String userId = "user123";
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));

        ResponseEntity<Void> responseEntity = orderService.deleteAllCartItems(userId);

        assertEquals(ResponseEntity.noContent().build(), responseEntity);
        verify(orderRepository, times(1)).delete(existingOrder);
    }

    @Test
    void deleteAllCartItems_OrderDoesNotExist() {
        String userId = "user123";
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ResponseEntity<Void> responseEntity = orderService.deleteAllCartItems(userId);

        assertEquals(ResponseEntity.noContent().build(), responseEntity);
        verify(orderRepository, never()).delete(any());
    }

    @Test
    void deleteCartItem_OrderExists() {
        String userId = "user123";
        String bookingId = "booking123";
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));

        ResponseEntity<Void> responseEntity = orderService.deleteCartItemByBookingIdAndOrderId(bookingId, userId);

        assertEquals(ResponseEntity.ok().build(), responseEntity);
        verify(cartItemRepository, times(1)).deleteByBookingIdAndOrderId(bookingId, existingOrder.getId());
    }

    @Test
    void decreaseCartItem_OrderAndCartItemExist() {
        String userId = "user123";
        String bookingId = "booking123";
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        CartItem existingCartItem = CartItem.builder().build();
        existingCartItem.setId("cartItem123");
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));
        when(cartItemRepository.findByBookingIdAndOrderId(bookingId, existingOrder.getId())).thenReturn(Optional.of(existingCartItem));

        ResponseEntity<CartItem> responseEntity = orderService.decreaseCartItem(new AddToCartRequest(userId, bookingId, 100));

        assertEquals(ResponseEntity.ok(existingCartItem).getStatusCode(), HttpStatus.OK);
        verify(cartItemRepository, times(1)).save(any());
    }

    @Test
    void findCartItemsQuantity_OrderExists() {
        String userId = "user123";
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        int expectedQuantity = 10;
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));
        when(cartItemMongoTemplate.getTotalQuantityByOrderId(existingOrder.getId())).thenReturn(expectedQuantity);

        ResponseEntity<Integer> responseEntity = orderService.findCartItemsQuantity(userId);

        assertEquals(ResponseEntity.ok(expectedQuantity), responseEntity);
    }

    @Test
    void findCartItemsQuantity_OrderDoesNotExist() {
        String userId = "user123";
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ResponseEntity<Integer> responseEntity = orderService.findCartItemsQuantity(userId);

        assertEquals(ResponseEntity.ok(0), responseEntity);
    }

    @Test
    void findCartItemsTotalPrice() {
        String userId = "user123";
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        int expectedTotalPrice = 10;
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));
        when(cartItemMongoTemplate.getTotalPriceByOrderId(existingOrder.getId())).thenReturn(expectedTotalPrice);

        // Test
        ResponseEntity<Integer> responseEntity = orderService.findCartItemsQuantity(userId);

        // Assertions
        assertEquals(ResponseEntity.ok(expectedTotalPrice).getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findCartItems() {
        String userId = "user123";
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        List<CartItem> expectedCartItems = Arrays.asList(
                CartItem.builder().id("1").bookingId("2").build(),
                CartItem.builder().id("3").bookingId("4").build());
        when(orderRepository.findByUserId(userId)).thenReturn(Optional.of(existingOrder));
        when(cartItemRepository.findAllByOrderId(existingOrder.getId())).thenReturn(expectedCartItems);

        ResponseEntity<List<CartItem>> responseEntity = orderService.findCartItems(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCartItems, responseEntity.getBody());
    }
}