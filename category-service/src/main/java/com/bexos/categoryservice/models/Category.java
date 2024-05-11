package com.bexos.categoryservice.models;

import com.bexos.categoryservice.dto.ImageModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("Category")
@Document(value = "Category")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private CategoryCode code;
    private ImageModel icon;

}
