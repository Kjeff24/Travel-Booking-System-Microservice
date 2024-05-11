package com.bexos.bookingservice.services.impl;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.feign.CategoryClient;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.repositories.AccommodationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AccommodationServiceImplTest {

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private CategoryClient categoryClient;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccommodationOffer_CategoryExists_ReturnsCreatedResponse() {
        AccommodationRequest accommodationRequest = AccommodationRequest.builder()
                .categoryId("123")
                .build();
        Accommodation newAccommodation = Accommodation.builder()
                .categoryId("123")
                .build();

        when(bookingMapper.toAccommodation(accommodationRequest)).thenReturn(newAccommodation);
        when(accommodationRepository.save(newAccommodation)).thenReturn(newAccommodation);
        when(categoryClient.existsCategoryById(anyString())).thenReturn(true);

        ResponseEntity<?> response = accommodationService.createAccommodationOffer(accommodationRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newAccommodation, response.getBody());
    }

    @Test
    void createAccommodationOffer_InvalidCategory_ReturnsBadRequestResponse() {
        AccommodationRequest accommodationRequest = AccommodationRequest.builder()
                .categoryId("123")
                .build();
        when(categoryClient.existsCategoryById(anyString())).thenReturn(false);

        ResponseEntity<?> response = accommodationService.createAccommodationOffer(accommodationRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Category does not exist", response.getBody());
    }

    @Test
    void findAccommodationById_AccommodationExists_ReturnsOkResponse() {
        String accommodationId  = "123";
        Accommodation accommodation = new Accommodation();
        accommodation.setId("123");

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));

        ResponseEntity<Accommodation> response = accommodationService.findAccommodationById(accommodationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accommodation, response.getBody());
    }

    @Test
    void findAccommodationById_AccommodationDoesNotExist_ReturnsNullResponse() {

        String accommodationId = "123";

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.empty());

        ResponseEntity<Accommodation> response = accommodationService.findAccommodationById(accommodationId);

        assertEquals(null, response);
    }

    @Test
    void updateAccommodation_AccommodationExists_ReturnsOkResponse() {
        String bookingId = "123";
        AccommodationRequest request = AccommodationRequest.builder().build();
        Accommodation accommodation = new Accommodation();
        accommodation.setId("123");

        when(accommodationRepository.findById(bookingId)).thenReturn(Optional.of(accommodation));
        when(accommodationRepository.save(any())).thenReturn(accommodation);

        ResponseEntity<?> response = accommodationService.updateAccommodation(bookingId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accommodation, response.getBody());
    }

    @Test
    void updateAccommodation_AccommodationDoesNotExist_ReturnsBadRequestResponse() {
        String bookingId = "123";
        AccommodationRequest request = AccommodationRequest.builder().build();

        when(accommodationRepository.findById(bookingId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = accommodationService.updateAccommodation(bookingId, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Accommodation does not exist", response.getBody());
    }

    @Test
    void findAllAccommodationsByCategory() {
        String categoryId = "123";
        List<Accommodation> accommodations = List.of(new Accommodation(), new Accommodation());

        when(accommodationRepository.findAllByCategoryId(categoryId)).thenReturn(Optional.of(accommodations));

        ResponseEntity<?> response = accommodationService.findAllAccommodationsByCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accommodations, response.getBody());
    }

    @Test
    void findAllAccommodations() {
        List<Accommodation> accommodations = List.of(new Accommodation(), new Accommodation());

        when(accommodationRepository.findAll()).thenReturn(accommodations);

        ResponseEntity<?> response = accommodationService.findAllAccommodations();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accommodations, response.getBody());
    }
}