package com.bexos.bookingservice.models.booking_categories;

import com.bexos.bookingservice.models.Booking;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@TypeAlias("Flight")
@Document
public class Flight extends Booking {
    private String departureCity;
    private String destinationCity;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private double price;
    private String categoryId;
}
