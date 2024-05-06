package com.bexos.paymentservice.mappers;

import com.bexos.paymentservice.dto.PaymentRequest;
import com.bexos.paymentservice.model.PaymentDetail;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public PaymentDetail toPaymentDetail(PaymentRequest request){
        return PaymentDetail.builder()
                .email(request.email())
                .exp_month(request.exp_month())
                .exp_year(request.exp_year())
                .last4(request.last4())
                .build();
    }
}
