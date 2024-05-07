package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CarRentalRepository extends MongoRepository<CarRental, Integer> {
    Optional<List<CarRental>> findAllByCategoryId(String categoryId);
    Optional<CarRental> findById(String id);
}
