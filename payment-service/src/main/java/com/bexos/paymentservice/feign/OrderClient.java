package com.bexos.paymentservice.feign;

import com.bexos.paymentservice.dto.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderClient {
    @GetMapping("/api/order-service/get-cart-items/{userId}")
    ResponseEntity<List<CartItem>> findCartItems(@PathVariable String userId);
}
