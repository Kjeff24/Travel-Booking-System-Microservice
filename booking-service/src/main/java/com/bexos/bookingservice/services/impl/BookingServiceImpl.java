package com.bexos.bookingservice.services.impl;

import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.repositories.AccommodationRepository;
import com.bexos.bookingservice.repositories.CarRentalRepository;
import com.bexos.bookingservice.repositories.FlightRepository;
import com.bexos.bookingservice.repositories.HotelRepository;
import com.bexos.bookingservice.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final AccommodationRepository accommodationRepository;
    private final CarRentalRepository carRentalRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;


    public ResponseEntity<?> findBookingOfferById(String bookingId) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(bookingId);
        if (accommodationOptional.isPresent()) {
            return ResponseEntity.ok(accommodationOptional.get());
        }
        Optional<Flight> flightOptional = flightRepository.findById(bookingId);
        if (flightOptional.isPresent()) {
            return ResponseEntity.ok(flightOptional.get());
        }
        Optional<CarRental> carRentalOptional = carRentalRepository.findById(bookingId);
        if (carRentalOptional.isPresent()) {
            return ResponseEntity.ok(carRentalOptional.get());
        }
        Optional<Hotel> hotelOptional = hotelRepository.findById(bookingId);
        return hotelOptional.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<Long> findNumberOfProducts() {
        return ResponseEntity.ok(accommodationRepository.count() + flightRepository.count() + carRentalRepository.count() + hotelRepository.count());

    }
}
