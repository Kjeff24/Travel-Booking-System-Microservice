package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @GetMapping("hello")
    public String hello() {
        return "Welcome to accommodation service secured";
    }

    @GetMapping("/accommodation")
    public ResponseEntity<List<Accommodation>> findAllAccommodations() {
        return bookingService.findAllAccommodations();
    }

    @PostMapping("/accommodation")
    public ResponseEntity<?> createAccommodationOffer(@Valid @RequestBody AccommodationRequest accommodationRequest) {
        return bookingService.createAccommodationOffer(accommodationRequest);
    }

    @GetMapping("/hotel")
    public ResponseEntity<List<Hotel>> findAllHotels() {
        return bookingService.findAllHotels();
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> createHotelOffer(@Valid @RequestBody HotelRequest hotelRequest) {
        return bookingService.createHotelOffer(hotelRequest);
    }

    @GetMapping("/flight")
    public ResponseEntity<List<Flight>> findAllFlights() {
        return bookingService.findAllFlights();
    }

    @PostMapping("/flight")
    public ResponseEntity<?> createFlightOffer(@Valid @RequestBody FlightRequest flightRequest) {
        return bookingService.createFlightOffer(flightRequest);
    }

    @GetMapping("/car-rental")
    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return bookingService.findAllCarRentals();
    }

    @PostMapping("/car-rental")
    public ResponseEntity<?> createCarRentalOffer(@Valid @RequestBody CarRentalRequest carRentalRequest) {
        return bookingService.createCarRentalOffer(carRentalRequest);
    }
}
