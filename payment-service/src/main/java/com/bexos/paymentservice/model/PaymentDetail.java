package com.bexos.paymentservice.model;

import com.bexos.paymentservice.dto.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("Payment-Detail")
@Document(value = "Payment-Details")
public class PaymentDetail {
    @Id
    private String id;
    private String userId;
    private String email;
    private String cardId;
    private int exp_month;
    private int exp_year;
    private int last4;
    private int totalCost;
    private List<CartItem> cartItems;
    @Builder.Default
    private LocalDateTime paymentDate = LocalDateTime.now();
}
