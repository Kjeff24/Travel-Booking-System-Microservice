package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarRentalService {
    ResponseEntity<List<CarRental>> findAllCarRentals();

    ResponseEntity<?> createCarRentalOffer(CarRentalRequest carRentalRequest);

    ResponseEntity<CarRental> findCarRentalById(String bookingId);

    ResponseEntity<?> updateCarRental(String bookingId, CarRentalRequest request);

    ResponseEntity<?> findCategoryByBookingId(String bookingId);

    ResponseEntity<?> findAllCarRentalsByCategory(String categoryId);
}
