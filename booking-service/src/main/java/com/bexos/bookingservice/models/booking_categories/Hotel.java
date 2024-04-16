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
@TypeAlias("Hotel")
@Document
public class Hotel extends Booking {
    private String hotelName;
    private String location;
    private String roomType;
    private double price;
    private String categoryId;
}
