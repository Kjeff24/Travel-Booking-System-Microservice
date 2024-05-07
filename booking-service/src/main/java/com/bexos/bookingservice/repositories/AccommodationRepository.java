package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends MongoRepository<Accommodation, String> {
    Optional<List<Accommodation>> findAllByCategoryId(String categoryId);
    @NotNull
    Optional<Accommodation> findById(@NotNull String id);
}
