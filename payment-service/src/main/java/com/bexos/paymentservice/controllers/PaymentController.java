package com.bexos.paymentservice.controllers;

import com.bexos.paymentservice.dto.PaymentRequest;
import com.bexos.paymentservice.model.PaymentDetail;
import com.bexos.paymentservice.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-service")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{userId}")
    ResponseEntity<?> makePayment(@RequestBody PaymentRequest request, @PathVariable String userId){
        return paymentService.makePayment(userId, request);
    }

    @GetMapping
    ResponseEntity<List<PaymentDetail>> findAllPayments(){
        return paymentService.findAllPayments();
    }
}
