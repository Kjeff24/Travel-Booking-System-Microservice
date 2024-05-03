package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.models.Booking;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<List<Accommodation>> findAllAccommodations();

    ResponseEntity<?> createAccommodationOffer(AccommodationRequest accommodationRequest);

    ResponseEntity<List<Hotel>> findAllHotels();

    ResponseEntity<?> createHotelOffer(HotelRequest hotelRequest);

    ResponseEntity<List<Flight>> findAllFlights();

    ResponseEntity<?> createFlightOffer(FlightRequest flightRequest);

    ResponseEntity<List<CarRental>> findAllCarRentals();

    ResponseEntity<?> createCarRentalOffer(CarRentalRequest carRentalRequest);

    ResponseEntity<?> findBookingOfferById(String bookingId);
}
