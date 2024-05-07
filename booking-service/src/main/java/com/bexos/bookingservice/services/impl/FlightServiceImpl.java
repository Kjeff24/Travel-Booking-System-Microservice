package com.bexos.bookingservice.services.impl;

import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.feign.CategoryClient;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.repositories.FlightRepository;
import com.bexos.bookingservice.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final BookingMapper bookingMapper;
    private final CategoryClient categoryClient;
    private final FlightRepository flightRepository;

    public ResponseEntity<?> createFlightOffer(FlightRequest flightRequest) {
        boolean existsById = categoryClient.existsCategoryById(flightRequest.categoryId());
        if(existsById){
            Flight newFlight = flightRepository.save(bookingMapper.toFlight(flightRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newFlight);
        }

        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<Flight> findFlightById(String bookingId) {
        Optional<Flight> flight = flightRepository.findById(bookingId);
        return flight.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<?> updateFlight(String bookingId, FlightRequest request) {
        Optional<Flight> flight = flightRepository.findById(bookingId);
        if (flight.isPresent()) {
            Flight flightToUpdate = flight.get();
            flightToUpdate.setDate(request.date());
            flightToUpdate.setDepartureCity(request.departureCity());
            flightToUpdate.setDestinationCity(request.destinationCity());
            flightToUpdate.setPrice(request.price());
            return ResponseEntity.ok(flightRepository.save(flightToUpdate));
        }
        return ResponseEntity.badRequest().body("Accommodation does not exist");
    }

    public ResponseEntity<?> findCategoryByBookingId(String bookingId) {
        Optional<Flight> flight = flightRepository.findById(bookingId);
        return flight.map(value -> ResponseEntity.ok(categoryClient.findCategoryById(value.getCategoryId()))).orElse(null);
    }

    public ResponseEntity<?> findAllFlightsByCategory(String categoryId) {
        Optional<List<Flight>> flights = flightRepository.findAllByCategoryId(categoryId);
        return flights.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<List<Flight>> findAllFlights() {
        return ResponseEntity.ok(flightRepository.findAll());
    }


}
