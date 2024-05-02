package com.bexos.cartservice.models;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@TypeAlias("Order")
@Document(value = "Order")
public class Order {
    @Id
    private String id;
    private String userId;
}
