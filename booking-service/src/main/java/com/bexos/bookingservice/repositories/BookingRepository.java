package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
