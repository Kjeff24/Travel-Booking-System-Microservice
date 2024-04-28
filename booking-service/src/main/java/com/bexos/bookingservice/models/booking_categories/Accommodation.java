package com.bexos.bookingservice.models.booking_categories;

import com.bexos.bookingservice.models.Booking;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@TypeAlias("Accommodation")
@Document
public class Accommodation extends Booking {
    private String location;
    private String type;
    private String capacity;
    private double price;
    private String categoryId;
}
