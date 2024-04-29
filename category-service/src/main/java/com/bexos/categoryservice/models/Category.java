package com.bexos.categoryservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@TypeAlias("Category")
@Document(value = "Category")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private CategoryCode code;
    private String icon;

}
