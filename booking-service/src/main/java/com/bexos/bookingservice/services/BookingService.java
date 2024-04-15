package com.bexos.bookingservice.services;

import com.bexos.bookingservice.models.Booking;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<List<Booking>> findAllBookings();
    ResponseEntity<Booking> findBookingById(ObjectId id);
}
