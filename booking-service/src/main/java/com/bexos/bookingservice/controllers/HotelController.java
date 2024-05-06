package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> findAllHotels() {
        return hotelService.findAllHotels();
    }

    @GetMapping("/find-category-by-booking-id/{bookingId}")
    public ResponseEntity<?> findCategoryByBookingId(@PathVariable String bookingId) {
        return hotelService.findCategoryByBookingId(bookingId);
    }

    @PostMapping
    public ResponseEntity<?> createHotelOffer(@Valid @RequestBody HotelRequest hotelRequest) {
        return hotelService.createHotelOffer(hotelRequest);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable String bookingId) {
        return hotelService.findHotelById(bookingId);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateHotel(@PathVariable String bookingId, @RequestBody HotelRequest request) {
        return hotelService.updateHotel(bookingId, request);
    }
}
