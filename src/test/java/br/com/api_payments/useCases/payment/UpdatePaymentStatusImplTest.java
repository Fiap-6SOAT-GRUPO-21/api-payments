package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.integration.mercadopago.payment.response.MerchantOrderResponse;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.useCases.payment.FindPaymentById;
import br.com.api_payments.infra.gateways.payment.MercadoPagoClient;
import br.com.api_payments.infra.persistence.repositories.payment.PaymentPersistencePortImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UpdatePaymentStatusImplTest {

    @Mock
    private PaymentPersistencePortImpl persistencePort;

    @Mock
    private FindPaymentById findPaymentById;

    @Mock
    private MercadoPagoClient mercadoPagoClient;

    @InjectMocks
    private UpdatePaymentStatusImpl updatePaymentStatus;

    @Value("${mercado-pago.access-token}")
    private String accessToken;

    @Test
    void shouldUpdatePaymentStatus() {

        // Arrange
        Long merchantOrderId = 12345L;
        String externalReference = String.valueOf(UUID.randomUUID());
        String status = "opened";

        MerchantOrderResponse merchantOrderResponse = new MerchantOrderResponse();
        merchantOrderResponse.setExternalReference(externalReference);
        merchantOrderResponse.setStatus(status);

        PaymentDomain paymentDomain = new PaymentDomain();
        paymentDomain.setId(UUID.fromString(externalReference));
        paymentDomain.setStatus(PaymentStatus.PENDING);

        doReturn(merchantOrderResponse).when(mercadoPagoClient).getOrder(accessToken, merchantOrderId);
        when(findPaymentById.execute(externalReference)).thenReturn(paymentDomain);

        // Act
        updatePaymentStatus.execute(merchantOrderId);

        // Assert
        assertThat(paymentDomain.getStatus())
                .isNotNull()
                .isEqualTo(PaymentStatus.fromString(status));

        verify(mercadoPagoClient).getOrder(accessToken, merchantOrderId);
        verify(findPaymentById).execute(externalReference);
        verify(persistencePort).save(argThat(payment ->
                payment.getId().toString().equals(externalReference) &&
                        payment.getStatus() == PaymentStatus.fromString(status)
        ));
        verifyNoMoreInteractions(mercadoPagoClient, findPaymentById, persistencePort);
    }

}