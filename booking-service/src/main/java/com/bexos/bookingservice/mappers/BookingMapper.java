package com.bexos.bookingservice.mappers;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.dto.FlightRequest;
import com.bexos.bookingservice.dto.HotelRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.models.booking_categories.Flight;
import com.bexos.bookingservice.models.booking_categories.Hotel;
import org.springframework.stereotype.Service;

@Service
public class BookingMapper {

    public Accommodation toAccommodation(AccommodationRequest accommodationRequest) {
        return Accommodation.builder()
                .location(accommodationRequest.location())
                .type(accommodationRequest.type())
                .capacity(accommodationRequest.capacity())
                .price(accommodationRequest.price())
                .categoryId(accommodationRequest.categoryId())
                .build();
    }

    public Hotel toHotel(HotelRequest hotelRequest) {
        return Hotel.builder()
                .hotelName(hotelRequest.hotelName())
                .location(hotelRequest.location())
                .roomType(hotelRequest.roomType())
                .price(hotelRequest.price())
                .categoryId(hotelRequest.categoryId())
                .build();
    }

    public Flight toFlight(FlightRequest flightRequest) {
        return Flight.builder()
                .departureCity(flightRequest.departureCity())
                .destinationCity(flightRequest.destinationCity())
                .price(flightRequest.price())
                .categoryId(flightRequest.categoryId())
                .build();
    }

    public CarRental toCarRental(CarRentalRequest carRentalRequest) {
        return CarRental.builder()
                .carType(carRentalRequest.carType())
                .carImage(carRentalRequest.carImage())
                .price(carRentalRequest.price())
                .categoryId(carRentalRequest.categoryId())
                .build();
    }
}
