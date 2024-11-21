package br.com.api_payments.domain.persistence.payment;

import br.com.api_payments.domain.entity.payment.PaymentDomain;

import java.util.Optional;
import java.util.UUID;

public interface PaymentPersistence {

    PaymentDomain save(PaymentDomain paymentDomain);

    PaymentDomain findById(String idPayment);
}
