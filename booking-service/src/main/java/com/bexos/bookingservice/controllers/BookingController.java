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
import com.bexos.bookingservice.services.BookingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingServiceImpl;
    private final BookingMapper bookingMapper;

    @GetMapping
    public ResponseEntity<List<Booking>> findAllBookings(){
        return bookingServiceImpl.findAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findBookingById(@PathVariable ObjectId id){
        return bookingServiceImpl.findBookingById(id);
    }

    @PostMapping("/accommodation")
    public ResponseEntity<Accommodation> createAccommodationOffer(@Valid @RequestBody AccommodationRequest accommodationRequest) {
        Accommodation booking = bookingServiceImpl.createBookingOffer(bookingMapper.toAccommodation(accommodationRequest));
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/hotel")
    public ResponseEntity<Hotel> createHotelOffer(@Valid @RequestBody HotelRequest hotelRequest) {
        Hotel booking = bookingServiceImpl.createBookingOffer(bookingMapper.toHotel(hotelRequest));
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/flight")
    public ResponseEntity<Flight> createFlightOffer(@Valid @RequestBody FlightRequest flightRequest) {
        Flight booking = bookingServiceImpl.createBookingOffer(bookingMapper.toFlight(flightRequest));
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/car-rental")
    public ResponseEntity<CarRental> createCarRentalOffer(@Valid @RequestBody CarRentalRequest carRentalRequest) {
        CarRental booking = bookingServiceImpl.createBookingOffer(bookingMapper.toCarRental(carRentalRequest));
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
}
