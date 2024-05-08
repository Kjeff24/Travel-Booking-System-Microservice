package com.bexos.bookingservice.services.impl;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.feign.CategoryClient;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.repositories.AccommodationRepository;
import com.bexos.bookingservice.services.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private final BookingMapper bookingMapper;
    private final CategoryClient categoryClient;
    private final AccommodationRepository accommodationRepository;

    public ResponseEntity<?> createAccommodationOffer(AccommodationRequest accommodationRequest) {
        boolean existsById = categoryClient.existsCategoryById(accommodationRequest.categoryId());
        if (existsById) {
            Accommodation newAccommodation = accommodationRepository.save(bookingMapper.toAccommodation(accommodationRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccommodation);
        }
        return ResponseEntity.badRequest().body("Category does not exist");
    }

    public ResponseEntity<Accommodation> findAccommodationById(String bookingId) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(bookingId);
        return accommodation.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<?> updateAccommodation(String bookingId, AccommodationRequest request) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(bookingId);
        if (accommodation.isPresent()) {
            Accommodation accommodationToUpdate = accommodation.get();
            accommodationToUpdate.setLocation(request.location());
            accommodationToUpdate.setType(request.type());
            accommodationToUpdate.setCapacity(request.capacity());
            accommodationToUpdate.setPrice(request.price());
            return ResponseEntity.ok(accommodationRepository.save(accommodationToUpdate));
        }
        return ResponseEntity.badRequest().body("Accommodation does not exist");
    }

    public ResponseEntity<?> findAllAccommodationsByCategory(String categoryId) {
        Optional<List<Accommodation>> accommodations = accommodationRepository.findAllByCategoryId(categoryId);
        return accommodations.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<List<Accommodation>> findAllAccommodations() {
        return ResponseEntity.ok(accommodationRepository.findAll());
    }


}
