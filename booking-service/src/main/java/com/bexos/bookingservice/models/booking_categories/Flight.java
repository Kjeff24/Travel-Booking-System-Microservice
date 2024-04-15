package com.bexos.bookingservice.models.booking_categories;

import com.bexos.bookingservice.models.Booking;
import com.bexos.bookingservice.models.CategoryCode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@TypeAlias("Flight")
@Document
public class Flight extends Booking {
    private String departureCity;
    private String destinationCity;
    private LocalDateTime date;
    private double price;
}
