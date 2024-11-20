package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.domain.entity.payment.PaymentDomain;

import java.util.UUID;

public interface FindPaymentById {

    PaymentDomain execute(String idPayment);
}
