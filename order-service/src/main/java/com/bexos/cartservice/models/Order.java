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
public class Order {
    @Id
    private String id;
    private String userId;
    @DBRef
    private List<OrderItem> items;
    private double totalAmount;
}
