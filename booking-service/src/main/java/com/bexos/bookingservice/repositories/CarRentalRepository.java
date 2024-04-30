package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.CarRental;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarRentalRepository extends MongoRepository<CarRental, Integer> {
    Optional<CarRental> findByCategoryId(String categoryId);

}
