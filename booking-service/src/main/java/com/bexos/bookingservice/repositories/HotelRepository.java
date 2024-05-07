package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends MongoRepository<Hotel, Long> {
    Optional<List<Hotel>> findAllByCategoryId(String categoryId);
    Optional<Hotel> findById(String id);
}
