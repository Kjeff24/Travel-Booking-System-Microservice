package com.bexos.paymentservice.services;

import com.bexos.paymentservice.dto.CartItem;
import com.bexos.paymentservice.dto.PaymentRequest;
import com.bexos.paymentservice.feign.OrderClient;
import com.bexos.paymentservice.mappers.PaymentMapper;
import com.bexos.paymentservice.model.PaymentDetail;
import com.bexos.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderClient orderClient;

    public ResponseEntity<?> makePayment(PaymentRequest request) {
        PaymentDetail paymentDetail = paymentMapper.toPaymentDetail(request);
        return ResponseEntity.ok(paymentRepository.save(paymentDetail));
    }

    public ResponseEntity<List<PaymentDetail>> findAllPayments() {
        List<PaymentDetail> payments = paymentRepository.findAll();
        return ResponseEntity.ok(payments);
    }

    public ResponseEntity<Long> findNumberOfPayments() {
        return ResponseEntity.ok(paymentRepository.count());
    }

    public ResponseEntity<?> deletePaymentById(String paymentId) {
        Optional<PaymentDetail> paymentDetail = paymentRepository.findById(paymentId);
        if (paymentDetail.isPresent()) {
            paymentRepository.deleteById(paymentId);
        }
        return ResponseEntity.ok().build();
    }
}
