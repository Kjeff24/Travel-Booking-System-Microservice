package com.bexos.bookingservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@TypeAlias("Booking")
@Document
public abstract class Booking implements Serializable {
    @Id
    private String id;

}
