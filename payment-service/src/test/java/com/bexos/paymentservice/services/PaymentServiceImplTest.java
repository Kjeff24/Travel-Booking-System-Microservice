package com.bexos.paymentservice.services;

import com.bexos.paymentservice.dto.PaymentRequest;
import com.bexos.paymentservice.mappers.PaymentMapper;
import com.bexos.paymentservice.model.PaymentDetail;
import com.bexos.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makePayment() {
        PaymentRequest paymentRequest = PaymentRequest.builder().build();
        PaymentDetail paymentDetail = new PaymentDetail();
        when(paymentRepository.save(any())).thenReturn(paymentDetail);

        ResponseEntity<?> responseEntity = paymentService.makePayment(paymentRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void findAllPayments() {
        List<PaymentDetail> expectedPayments = Arrays.asList(PaymentDetail.builder().build(), PaymentDetail.builder().build());
        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        ResponseEntity<List<PaymentDetail>> responseEntity = paymentService.findAllPayments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPayments, responseEntity.getBody());
    }

    @Test
    void findNumberOfPayments() {
        long expectedCount = 5;
        when(paymentRepository.count()).thenReturn(expectedCount);

        ResponseEntity<Long> responseEntity = paymentService.findNumberOfPayments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCount, responseEntity.getBody());
    }

    @Test
    void deletePaymentById_paymentExists() {
        String paymentId = "payment123";
        PaymentDetail paymentDetail = PaymentDetail.builder().build();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paymentDetail));

        ResponseEntity<?> responseEntity = paymentService.deletePaymentById(paymentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(paymentRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void deletePaymentById_paymentNotExists() {
        String paymentId = "payment123";
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = paymentService.deletePaymentById(paymentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(paymentRepository, never()).deleteById(paymentId);
    }
}