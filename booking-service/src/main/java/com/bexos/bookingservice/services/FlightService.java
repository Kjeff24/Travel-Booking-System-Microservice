package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FlightService {
    ResponseEntity<List<Flight>> findAllFlights();

    ResponseEntity<?> createFlightOffer(FlightRequest flightRequest);

    ResponseEntity<Flight> findFlightById(String bookingId);

    ResponseEntity<?> updateFlight(String bookingId, FlightRequest request);

    ResponseEntity<?> findAllFlightsByCategory(String categoryId);
}
