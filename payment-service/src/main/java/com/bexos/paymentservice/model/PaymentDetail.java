package com.bexos.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String orderId;
    private String email;
    private int exp_month;
    private int exp_year;
    private int last4;
}
