package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlightRepository extends MongoRepository<Flight, String> {
    Optional<Flight> findByCategoryId(String categoryId);
}