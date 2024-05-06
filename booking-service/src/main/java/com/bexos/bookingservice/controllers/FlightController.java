package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.services.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> findAllFlights() {
        return flightService.findAllFlights();
    }

    @GetMapping("/find-category-by-booking-id/{bookingId}")
    public ResponseEntity<?> findCategoryByBookingId(@PathVariable String bookingId) {
        return flightService.findCategoryByBookingId(bookingId);
    }

    @PostMapping
    public ResponseEntity<?> createFlightOffer(@Valid @RequestBody FlightRequest flightRequest) {
        return flightService.createFlightOffer(flightRequest);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Flight> findFlightById(@PathVariable String bookingId) {
        return flightService.findFlightById(bookingId);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateFlight(@PathVariable String bookingId, @RequestBody FlightRequest request) {
        return flightService.updateFlight(bookingId, request);
    }
}
