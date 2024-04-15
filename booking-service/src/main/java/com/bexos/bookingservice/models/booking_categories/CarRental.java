package com.bexos.bookingservice.models.booking_categories;

import com.bexos.bookingservice.models.Booking;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@TypeAlias("CarRental")
@Document
public class CarRental extends Booking {
    private String carType;
    private double pricePerDay;
    private String carImage;
}
