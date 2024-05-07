package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends MongoRepository<Flight, String> {
    Optional<List<Flight>> findAllByCategoryId(String categoryId);
    @NotNull
    Optional<Flight> findById(@NotNull String id);
}
