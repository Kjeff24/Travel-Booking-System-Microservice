package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.feign.CategoryClient;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.Booking;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final AccommodationRepository accommodationRepository;
    private final CarRentalRepository carRentalRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;
    private final CategoryClient categoryClient;
    private final BookingRepository bookingRepository;

    public ResponseEntity<?> createAccommodationOffer(AccommodationRequest accommodationRequest) {
        boolean existsById = categoryClient.existsCategoryById(accommodationRequest.categoryId());
        if(existsById){
            Accommodation newAccommodation = accommodationRepository.save(bookingMapper.toAccommodation(accommodationRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccommodation);
        }
        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<?> createHotelOffer(HotelRequest hotelRequest) {
        boolean existsById = categoryClient.existsCategoryById(hotelRequest.categoryId());
        if(existsById){
            Hotel newHotel = hotelRepository.save(bookingMapper.toHotel(hotelRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
        }

        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<?> createFlightOffer(FlightRequest flightRequest) {
        boolean existsById = categoryClient.existsCategoryById(flightRequest.categoryId());
        if(existsById){
            Flight newFlight = flightRepository.save(bookingMapper.toFlight(flightRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newFlight);
        }

        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<?> createCarRentalOffer(CarRentalRequest carRentalRequest) {
        boolean existsById = categoryClient.existsCategoryById(carRentalRequest.categoryId());
        if(existsById){
            CarRental newCarRental = carRentalRepository.save(bookingMapper.toCarRental(carRentalRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newCarRental);
        }

        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<?> findBookingOfferById(String bookingId) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(bookingId);
        if(accommodationOptional.isPresent()){
            return ResponseEntity.ok(accommodationOptional.get());
        }
        Optional<Flight> flightOptional = flightRepository.findById(bookingId);
        if(flightOptional.isPresent()){
            return ResponseEntity.ok(flightOptional.get());
        }
        Optional<CarRental> carRentalOptional = carRentalRepository.findById(bookingId);
        if(carRentalOptional.isPresent()){
            return ResponseEntity.ok(carRentalOptional.get());
        }
        Optional<Hotel> hotelOptional = hotelRepository.findById(bookingId);
        return hotelOptional.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<List<Accommodation>> findAllAccommodations() {
        return ResponseEntity.ok(accommodationRepository.findAll());
    }

    public ResponseEntity<List<Hotel>> findAllHotels() {
        return ResponseEntity.ok(hotelRepository.findAll());
    }

    public ResponseEntity<List<Flight>> findAllFlights() {
        return ResponseEntity.ok(flightRepository.findAll());
    }

    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return ResponseEntity.ok(carRentalRepository.findAll());
    }
}
