package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HotelService {
    ResponseEntity<?> createHotelOffer(HotelRequest hotelRequest);

    ResponseEntity<List<Hotel>> findAllHotels();

    ResponseEntity<Hotel> findHotelById(String bookingId);

    ResponseEntity<?> updateHotel(String bookingId, HotelRequest request);

    ResponseEntity<?> findCategoryByBookingId(String bookingId);

    ResponseEntity<?> findAllHotelsByCategory(String categoryId);
}
