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

    @PostMapping
    ResponseEntity<?> makePayment(@RequestBody PaymentRequest request){
        return paymentService.makePayment(request);
    }

    @GetMapping
    ResponseEntity<List<PaymentDetail>> findAllPayments(){
        return paymentService.findAllPayments();
    }

    @GetMapping("/number-of-payments")
    public ResponseEntity<Long> findNumberOfPayments() {
        return paymentService.findNumberOfPayments();
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePaymentById(@PathVariable String paymentId){
        return paymentService.deletePaymentById(paymentId);
    }
}
