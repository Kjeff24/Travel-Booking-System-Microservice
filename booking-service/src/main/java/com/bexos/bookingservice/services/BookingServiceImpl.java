package com.bexos.bookingservice.services;

import com.bexos.bookingservice.models.Booking;
import com.bexos.bookingservice.models.Category;
import com.bexos.bookingservice.repositories.BookingRepository;
import com.bexos.bookingservice.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl {
    private final BookingRepository bookingRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<List<Booking>> findAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    public ResponseEntity<Booking> findBookingById(ObjectId id) {
        return bookingRepository.findById(String.valueOf(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public <T extends Booking> T createBookingOffer(T booking) {
        return bookingRepository.save(booking);
    }
}
