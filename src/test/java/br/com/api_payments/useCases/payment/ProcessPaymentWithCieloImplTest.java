package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.payment.PaymentIntegrationItem;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProcessPaymentWithCieloImplTest {

    private final ProcessPaymentWithCieloImpl processPaymentWithCielo = new ProcessPaymentWithCieloImpl();

    @Test
    void processPayment_shouldThrowRuntimeException() {
        // Arrange
        PaymentIntegrationOrder order = new PaymentIntegrationOrder(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                List.of(new PaymentIntegrationItem(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100), "Test Item"))
        );

        // Act & Assert
        assertThatThrownBy(() -> processPaymentWithCielo.processPayment(order))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not implemented");
    }
}