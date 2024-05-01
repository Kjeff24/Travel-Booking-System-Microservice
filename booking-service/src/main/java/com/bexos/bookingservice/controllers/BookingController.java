package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.Booking;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.repositories.AccommodationRepository;
import com.bexos.bookingservice.services.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    private final BookingServiceImpl bookingServiceImpl;
    private final BookingMapper bookingMapper;

    @GetMapping("hello")
    public String hello() {
        return "Welcome to accommodation service secured";
    }

    @GetMapping("/accommodation")
    public ResponseEntity<List<Accommodation>> findAllAccommodations() {
        return bookingServiceImpl.findAllAccommodations();
    }

    @PostMapping("/accommodation")
    public ResponseEntity<?> createAccommodationOffer(@Valid @RequestBody AccommodationRequest accommodationRequest) {
        return bookingServiceImpl.createAccommodationOffer(accommodationRequest);
    }

    @GetMapping("/hotel")
    public ResponseEntity<List<Hotel>> findAllHotels() {
        return bookingServiceImpl.findAllHotels();
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> createHotelOffer(@Valid @RequestBody HotelRequest hotelRequest) {
        return bookingServiceImpl.createHotelOffer(hotelRequest);
    }

    @GetMapping("/flight")
    public ResponseEntity<List<Flight>> findAllFlights() {
        return bookingServiceImpl.findAllFlights();
    }

    @PostMapping("/flight")
    public ResponseEntity<?> createFlightOffer(@Valid @RequestBody FlightRequest flightRequest) {
        return bookingServiceImpl.createFlightOffer(flightRequest);
    }

    @GetMapping("/car-rental")
    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return bookingServiceImpl.findAllCarRentals();
    }

    @PostMapping("/car-rental")
    public ResponseEntity<?> createCarRentalOffer(@Valid @RequestBody CarRentalRequest carRentalRequest) {
        return bookingServiceImpl.createCarRentalOffer(carRentalRequest);
    }
}
