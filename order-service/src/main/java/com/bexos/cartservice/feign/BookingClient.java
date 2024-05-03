package com.bexos.cartservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BOOKING-SERVICE")
public interface BookingClient {

    @GetMapping("/api/booking-service/find-by-id/{bookingId}")
    ResponseEntity<?> findBookingOfferById(@PathVariable String bookingId);
}
