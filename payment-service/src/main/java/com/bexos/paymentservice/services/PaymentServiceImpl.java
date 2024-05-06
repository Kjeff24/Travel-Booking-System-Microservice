package com.bexos.paymentservice.services;

import com.bexos.paymentservice.dto.PaymentRequest;
import com.bexos.paymentservice.mappers.PaymentMapper;
import com.bexos.paymentservice.model.PaymentDetail;
import com.bexos.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public ResponseEntity<?> makePayment(String userId, PaymentRequest request) {
        PaymentDetail paymentDetail = paymentMapper.toPaymentDetail(request);
        paymentDetail.setUserId(userId);
        return ResponseEntity.ok(paymentRepository.save(paymentDetail));
    }

    public ResponseEntity<List<PaymentDetail>> findAllPayments() {
        List<PaymentDetail> payments = paymentRepository.findAll();
        return ResponseEntity.ok(payments);
    }
}
