package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationResult;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import br.com.api_payments.domain.useCases.payment.ProcessPayment;
import org.springframework.stereotype.Component;

@Component(PaymentType.CIELO_QUALIFIER)
public class ProcessPaymentWithCieloImpl implements ProcessPayment {

    @Override
    public PaymentIntegrationResult processPayment(PaymentIntegrationOrder paymentIntegrationOrder) {
        System.out.println("Processing payment with Cielo");
        throw new RuntimeException("Not implemented");
    }
}
