package br.com.api_payments.useCases.payment;

import br.com.api_payments.useCases.payment.exceptions.PaymentNotFound;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.persistence.payment.PaymentPersistence;
import br.com.api_payments.domain.useCases.payment.FindPaymentById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindPaymentByIdImpl implements FindPaymentById {

    final PaymentPersistence paymentPersistence;
    @Override
    public PaymentDomain execute(UUID idPayment) {
        return paymentPersistence.findById(idPayment).orElseThrow(PaymentNotFound::new);
    }
}
