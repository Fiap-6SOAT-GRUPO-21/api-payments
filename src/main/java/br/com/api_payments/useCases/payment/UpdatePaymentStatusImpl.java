package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.integration.mercadopago.payment.response.MerchantOrderResponse;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.useCases.payment.FindPaymentById;
import br.com.api_payments.domain.useCases.payment.UpdatePaymentStatus;
import br.com.api_payments.infra.gateways.payment.MercadoPagoClient;
import br.com.api_payments.infra.persistence.repositories.payment.PaymentPersistencePortImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePaymentStatusImpl implements UpdatePaymentStatus {

    private final PaymentPersistencePortImpl persistencePort;
    private final FindPaymentById findPaymentById;
    private final MercadoPagoClient mercadoPagoClient;

    @Value("${mercado-pago.access-token}")
    private String accessToken;

    @Override
    public void execute(Long merchantOrderId) {

        MerchantOrderResponse response = mercadoPagoClient.getOrder(accessToken, merchantOrderId);
        PaymentDomain paymentDomain = findPaymentById.execute(response.getExternalReference());
        paymentDomain.setStatus(PaymentStatus.fromString(response.getStatus()));

        persistencePort.save(paymentDomain);
        log.info("Payment status updated.");

    }
}
