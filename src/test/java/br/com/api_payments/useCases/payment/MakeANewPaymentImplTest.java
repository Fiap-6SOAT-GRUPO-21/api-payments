package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.payment.NewPaymentDTO;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationResult;
import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.application.dtos.payment.order.OrderItemDTO;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import br.com.api_payments.domain.persistence.payment.PaymentPersistence;
import br.com.api_payments.domain.useCases.payment.ProcessPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakeANewPaymentImplTest {

    @Mock
    private PaymentPersistence paymentPersistence;

    @Mock
    private Map<String, ProcessPayment> processPaymentList;

    @Mock
    private ProcessPayment processPayment;

    @InjectMocks
    private MakeANewPaymentImpl makeANewPayment;

    private NewPaymentDTO newPaymentDTO;

    @BeforeEach
    void setUp() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIdStore(UUID.randomUUID());
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.setTotal(BigDecimal.valueOf(100));
        orderDTO.setItems(List.of(new OrderItemDTO(UUID.randomUUID(), "Product A", BigDecimal.valueOf(25), UUID.randomUUID(), 4)));

        newPaymentDTO = new NewPaymentDTO(orderDTO, PaymentType.MERCADO_PAGO);
    }

    @Test
    void execute_shouldMakePaymentSuccessfully() {

        // Arrange
        PaymentIntegrationResult paymentIntegrationResult = new PaymentIntegrationResult(UUID.randomUUID(), "QR123");

        PaymentDomain expectedPaymentDomain = new PaymentDomain();
        expectedPaymentDomain.setId(paymentIntegrationResult.getPaymentId());
        expectedPaymentDomain.setAmount(BigDecimal.valueOf(100));
        expectedPaymentDomain.setQrCode("QR123");
        expectedPaymentDomain.setType(PaymentType.MERCADO_PAGO);
        expectedPaymentDomain.setStatus(PaymentStatus.PENDING);
        expectedPaymentDomain.setIdOrder(newPaymentDTO.getOrderDTO().getOrderId());

        when(processPaymentList.get(newPaymentDTO.getProvider().name())).thenReturn(processPayment);
        when(processPayment.processPayment(any(PaymentIntegrationOrder.class))).thenReturn(paymentIntegrationResult);
        when(paymentPersistence.save(any(PaymentDomain.class))).thenReturn(expectedPaymentDomain);

        // Act
        PaymentDomain result = makeANewPayment.execute(newPaymentDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedPaymentDomain.getId());
        assertThat(result.getAmount()).isEqualTo(expectedPaymentDomain.getAmount());
        assertThat(result.getQrCode()).isEqualTo(expectedPaymentDomain.getQrCode());
        assertThat(result.getType()).isEqualTo(expectedPaymentDomain.getType());
        assertThat(result.getStatus()).isEqualTo(expectedPaymentDomain.getStatus());
        assertThat(result.getIdOrder()).isEqualTo(expectedPaymentDomain.getIdOrder());

        verify(processPaymentList).get(newPaymentDTO.getProvider().name());
        verify(processPayment).processPayment(any(PaymentIntegrationOrder.class));
        verify(paymentPersistence).save(any(PaymentDomain.class));
    }

    @Test
    void execute_shouldThrowExceptionForInvalidProvider() {
        // Arrange
        when(processPaymentList.get(newPaymentDTO.getProvider().name())).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> makeANewPayment.execute(newPaymentDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid payment provider: MERCADO_PAGO");

        verify(processPaymentList).get(newPaymentDTO.getProvider().name());
        verifyNoInteractions(processPayment);
        verifyNoInteractions(paymentPersistence);
    }

}