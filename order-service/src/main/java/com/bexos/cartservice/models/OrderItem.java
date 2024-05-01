package com.bexos.cartservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@TypeAlias("Order_Item")
@Document(value = "ORDER_ITEMS")
public class OrderItem {

    @Id
    private String id;
    private String bookingId;
    private int quantity;
    private String orderId;
}
