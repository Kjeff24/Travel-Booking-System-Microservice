package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccommodationService {
    ResponseEntity<List<Accommodation>> findAllAccommodations();

    ResponseEntity<?> createAccommodationOffer(AccommodationRequest accommodationRequest);

    ResponseEntity<Accommodation> findAccommodationById(String bookingId);

    ResponseEntity<?> updateAccommodation(String bookingId, AccommodationRequest accommodationRequest);

    ResponseEntity<?> findCategoryByBookingId(String bookingId);

    ResponseEntity<?> findAllAccommodationsByCategory(String categoryId);
}
