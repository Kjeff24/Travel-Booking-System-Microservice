package com.bexos.bookingservice.services.impl;

import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.feign.CategoryClient;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.repositories.CarRentalRepository;
import com.bexos.bookingservice.services.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarRentalServiceImpl implements CarRentalService {
    private final BookingMapper bookingMapper;
    private final CategoryClient categoryClient;
    private final CarRentalRepository carRentalRepository;

    public ResponseEntity<?> createCarRentalOffer(CarRentalRequest carRentalRequest) {
        boolean existsById = categoryClient.existsCategoryById(carRentalRequest.categoryId());
        if(existsById){
            CarRental newCarRental = carRentalRepository.save(bookingMapper.toCarRental(carRentalRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newCarRental);
        }

        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<CarRental> findCarRentalById(String bookingId) {
        Optional<CarRental> carRental = carRentalRepository.findById(bookingId);
        return carRental.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<?> updateCarRental(String bookingId, CarRentalRequest request) {
        Optional<CarRental> carRental = carRentalRepository.findById(bookingId);
        if (carRental.isPresent()) {
            CarRental carRentalToUpdate = carRental.get();
            carRentalToUpdate.setCarImage(request.carImage());
            carRentalToUpdate.setCarType(request.carType());
            carRentalToUpdate.setPrice(request.price());
            return ResponseEntity.ok(carRentalRepository.save(carRentalToUpdate));
        }
        return ResponseEntity.badRequest().body("Car Rental does not exist");
    }

    public ResponseEntity<?> findCategoryByBookingId(String bookingId) {
        Optional<CarRental> carRental = carRentalRepository.findById(bookingId);
        return carRental.map(value -> ResponseEntity.ok(categoryClient.findCategoryById(value.getCategoryId()))).orElse(null);
    }

    public ResponseEntity<?> findAllCarRentalsByCategory(String categoryId) {
        Optional<List<CarRental>> carRentals = carRentalRepository.findAllByCategoryId(categoryId);
        return carRentals.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return ResponseEntity.ok(carRentalRepository.findAll());
    }

}
