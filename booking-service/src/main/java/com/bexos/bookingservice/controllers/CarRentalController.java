package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.ImageModel;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.services.CarRentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/booking-service/car-rental")
@RequiredArgsConstructor
public class CarRentalController {
    private final CarRentalService carRentalService;

    @GetMapping
    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return carRentalService.findAllCarRentals();
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<CarRental> findCarRentalById(@PathVariable String bookingId) {
        return carRentalService.findCarRentalById(bookingId);
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> findAllCarRentalsByCategory(@PathVariable String categoryId) {
        return carRentalService.findAllCarRentalsByCategory(categoryId);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCarRentalOffer(
            @Valid @RequestPart("carRentalRequest") CarRentalRequest carRentalRequest,
           @RequestPart("imageFile") MultipartFile image) {
        try {
            ImageModel carImage = uploadImage(image);
            return carRentalService.createCarRentalOffer(carRentalRequest, carImage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Image file upload failed, ensure you upload an image");
        }
    }

    public ImageModel uploadImage(MultipartFile file) throws IOException {
        return ImageModel.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .picByte(file.getBytes())
                .build();

    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateCarRental(
            @PathVariable String bookingId,
            @Valid @RequestPart("carRentalRequest") CarRentalRequest carRentalRequest,
            @RequestPart("imageFile") MultipartFile image) {
        try {
            ImageModel carImage = uploadImage(image);
            return carRentalService.updateCarRental(bookingId, carRentalRequest, carImage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Image file upload failed, ensure you upload an image");
        }
    }
}
