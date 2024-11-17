package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationResult;

public interface ProcessPayment {

    PaymentIntegrationResult processPayment(PaymentIntegrationOrder paymentIntegrationOrder);

}
