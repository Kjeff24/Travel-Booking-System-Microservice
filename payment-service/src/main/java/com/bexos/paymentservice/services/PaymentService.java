package com.bexos.paymentservice.services;


import com.bexos.paymentservice.dto.PaymentRequest;
import com.bexos.paymentservice.model.PaymentDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    ResponseEntity<?> makePayment(String usedId, PaymentRequest request);

    ResponseEntity<List<PaymentDetail>> findAllPayments();
}
