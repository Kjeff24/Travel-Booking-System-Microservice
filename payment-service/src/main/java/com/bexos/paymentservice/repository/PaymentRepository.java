package com.bexos.paymentservice.repository;

import com.bexos.paymentservice.model.PaymentDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentDetail, String> {
}
