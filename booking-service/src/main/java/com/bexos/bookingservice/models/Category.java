package com.bexos.bookingservice.models;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@TypeAlias("Category")
@Document(value = "category")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private CategoryCode code;
    private String icon;

}
