package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<?> createAccommodationOffer(AccommodationRequest accommodationRequest);

    ResponseEntity<?> createHotelOffer(HotelRequest hotelRequest);

    ResponseEntity<?> createFlightOffer(FlightRequest flightRequest);

    ResponseEntity<?> createCarRentalOffer(CarRentalRequest carRentalRequest);

    ResponseEntity<List<Accommodation>> findAllAccommodations();

    ResponseEntity<List<Hotel>> findAllHotels();

    ResponseEntity<List<Flight>> findAllFlights();

    ResponseEntity<List<CarRental>> findAllCarRentals();
}
