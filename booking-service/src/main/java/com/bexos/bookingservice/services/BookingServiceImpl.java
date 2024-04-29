package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.mappers.BookingMapper;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import com.bexos.bookingservice.repositories.AccommodationRepository;
import com.bexos.bookingservice.repositories.CarRentalRepository;
import com.bexos.bookingservice.repositories.FlightRepository;
import com.bexos.bookingservice.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final AccommodationRepository accommodationRepository;
    private final CarRentalRepository carRentalRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;

    public ResponseEntity<Accommodation> createAccommodationOffer(AccommodationRequest accommodationRequest) {
        Accommodation newAccommodation = accommodationRepository.save(bookingMapper.toAccommodation(accommodationRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccommodation);
    }

    public ResponseEntity<Hotel> createHotelOffer(HotelRequest hotelRequest) {
        Hotel newHotel = hotelRepository.save(bookingMapper.toHotel(hotelRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
    }

    public ResponseEntity<Flight> createFlightOffer(FlightRequest flightRequest) {
        Flight newFlight = flightRepository.save(bookingMapper.toFlight(flightRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(newFlight);
    }

    public ResponseEntity<CarRental> createCarRentalOffer(CarRentalRequest carRentalRequest) {
        CarRental newCarRental = carRentalRepository.save(bookingMapper.toCarRental(carRentalRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(newCarRental);
    }

    public ResponseEntity<List<Accommodation>> findAllAccommodations() {
        return ResponseEntity.ok(accommodationRepository.findAll());
    }

    public ResponseEntity<List<Hotel>> findAllHotels() {
        return ResponseEntity.ok(hotelRepository.findAll());
    }

    public ResponseEntity<List<Flight>> findAllFlights() {
        return ResponseEntity.ok(flightRepository.findAll());
    }

    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return ResponseEntity.ok(carRentalRepository.findAll());
    }
}
