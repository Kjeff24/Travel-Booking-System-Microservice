package com.bexos.bookingservice.services.impl;

import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.feign.CategoryClient;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.repositories.HotelRepository;
import com.bexos.bookingservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final BookingMapper bookingMapper;
    private final CategoryClient categoryClient;
    private final HotelRepository hotelRepository;

    public ResponseEntity<?> createHotelOffer(HotelRequest hotelRequest) {
        boolean existsById = categoryClient.existsCategoryById(hotelRequest.categoryId());
        if(existsById){
            Hotel newHotel = hotelRepository.save(bookingMapper.toHotel(hotelRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
        }

        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<List<Hotel>> findAllHotels() {
        return ResponseEntity.ok(hotelRepository.findAll());
    }

    public ResponseEntity<Hotel> findHotelById(String bookingId) {
        Optional<Hotel> hotel = hotelRepository.findById(bookingId);
        return hotel.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<?> updateHotel(String bookingId, HotelRequest request) {
        Optional<Hotel> hotel = hotelRepository.findById(bookingId);
        if (hotel.isPresent()) {
            Hotel hotelToUpdate = hotel.get();
            hotelToUpdate.setLocation(request.location());
            hotelToUpdate.setRoomType(request.roomType());
            hotelToUpdate.setHotelName(request.hotelName());
            hotelToUpdate.setPrice(request.price());
            return ResponseEntity.ok(hotelRepository.save(hotelToUpdate));
        }
        return ResponseEntity.badRequest().body("Accommodation does not exist");
    }

    public ResponseEntity<?> findCategoryByBookingId(String bookingId) {
        Optional<Hotel> hotel = hotelRepository.findById(bookingId);
        return hotel.map(value -> ResponseEntity.ok(categoryClient.findCategoryById(value.getCategoryId()))).orElse(null);
    }

    public ResponseEntity<?> findAllHotelsByCategory(String categoryId) {
        Optional<List<Hotel>> hotels = hotelRepository.findAllByCategoryId(categoryId);
        return hotels.map(ResponseEntity::ok).orElse(null);
    }


}
