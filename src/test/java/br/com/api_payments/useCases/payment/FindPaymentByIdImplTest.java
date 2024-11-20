package br.com.api_payments.useCases.payment;


import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import br.com.api_payments.domain.persistence.payment.PaymentPersistence;
import br.com.api_payments.useCases.payment.exceptions.PaymentNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindPaymentByIdImplTest {

    @Mock
    private PaymentPersistence paymentPersistence;

    @InjectMocks
    private FindPaymentByIdImpl findPaymentById;

    @Test
    void shouldReturnPayment_whenPaymentExists() {

        // Arrange
        var UUIDPayment = UUID.randomUUID();
        var idPayment = String.valueOf(UUIDPayment);
        var payment = new PaymentDomain(UUID.fromString(idPayment), UUID.randomUUID(), BigDecimal.valueOf(10), PaymentType.MERCADO_PAGO, "qr-code", PaymentStatus.PENDING);
        when(paymentPersistence.findById(idPayment)).thenReturn(Optional.of(payment));

        // Act
        var result = findPaymentById.execute(idPayment);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(payment)
                .extracting(PaymentDomain::getId)
                .isEqualTo(UUIDPayment);
    }

    @Test
    void execute_shouldThrowException_whenPaymentDoesNotExist() {
        // Arrange
        var idPayment = String.valueOf(UUID.randomUUID());
        when(paymentPersistence.findById(idPayment)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> findPaymentById.execute(idPayment))
                .isInstanceOf(PaymentNotFound.class)
                .hasMessageContaining("Payment not exists");
    }
  
}