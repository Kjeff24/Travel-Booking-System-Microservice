package com.bexos.bookingservice.services;

import org.springframework.http.ResponseEntity;

public interface BookingService {
    ResponseEntity<?> findBookingOfferById(String bookingId);

    ResponseEntity<?> findNumberOfProducts();
}
