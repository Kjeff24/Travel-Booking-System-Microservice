package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.Accommodation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccommodationRepository extends MongoRepository<Accommodation, String> {
    Optional<Accommodation> findByCategoryId(String categoryId);

}
