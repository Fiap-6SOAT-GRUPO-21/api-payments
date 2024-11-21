package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.config.UrlNotificationMercadoPagoConfig;
import br.com.api_payments.application.dtos.integration.mercadopago.payment.request.MercadoPagoRequest;
import br.com.api_payments.application.dtos.integration.mercadopago.payment.response.MercadoPagoResponse;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationItem;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationResult;
import br.com.api_payments.infra.gateways.internal.ApiFood;
import br.com.api_payments.infra.gateways.internal.dto.MercadoPagoGatewayDTO;
import br.com.api_payments.infra.gateways.internal.dto.StoreDomainDTO;
import br.com.api_payments.infra.gateways.payment.MercadoPagoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProcessPaymentWithMercadoPagoImplTest {

    @Mock
    private UrlNotificationMercadoPagoConfig urlNotificationMercadoPagoConfig;

    @Mock
    private MercadoPagoClient mercadoPagoClient;

    @Mock
    private ApiFood apiStore;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProcessPaymentWithMercadoPagoImpl processPaymentWithMercadoPago;

    private PaymentIntegrationOrder paymentIntegrationOrder;
    private StoreDomainDTO storeDomainDTO;
    private MercadoPagoResponse mercadoPagoResponse;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(processPaymentWithMercadoPago, "accessToken", "test-access-token");

        paymentIntegrationOrder = new PaymentIntegrationOrder(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                List.of(new PaymentIntegrationItem(2, BigDecimal.valueOf(50), BigDecimal.valueOf(25), "Item A"))
        );

        storeDomainDTO = new StoreDomainDTO();
        MercadoPagoGatewayDTO mercadoPagoGateway = new MercadoPagoGatewayDTO();
        mercadoPagoGateway.setCollectors("test-collector");
        mercadoPagoGateway.setExternalPos("test-pos");
        storeDomainDTO.setMercadoPagoGateway(mercadoPagoGateway);

        mercadoPagoResponse = new MercadoPagoResponse();
        mercadoPagoResponse.setQrData("test-qr-code");

        when(urlNotificationMercadoPagoConfig.getUrl()).thenReturn("http://notification.url");

        when(apiStore.findStoreById(paymentIntegrationOrder.getIdStore())).thenReturn(storeDomainDTO);
    }

    @Test
    void processPayment_shouldReturnPaymentIntegrationResult() {

        // Arrange
        when(mercadoPagoClient.createOrder(
                eq("test-access-token"),
                eq("test-collector"),
                eq("test-pos"),
                any(MercadoPagoRequest.class)
        )).thenReturn(mercadoPagoResponse);

        // Act
        PaymentIntegrationResult result = processPaymentWithMercadoPago.processPayment(paymentIntegrationOrder);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(paymentIntegrationOrder.getOrderPaymentId());
        assertThat(result.getQrCode()).isEqualTo("test-qr-code");

        verify(urlNotificationMercadoPagoConfig).getUrl();
        verify(apiStore).findStoreById(paymentIntegrationOrder.getIdStore());
        verify(mercadoPagoClient).createOrder(
                eq("test-access-token"),
                eq("test-collector"),
                eq("test-pos"),
                any(MercadoPagoRequest.class)
        );
    }

    @Test
    void processPayment_shouldPopulateRequestCorrectly() {

        // Arrange
        MercadoPagoRequest capturedRequest = new MercadoPagoRequest();

        doAnswer(invocation -> {
            capturedRequest.setNotificationUrl(invocation.getArgument(3, MercadoPagoRequest.class).getNotificationUrl());
            return mercadoPagoResponse;
        }).when(mercadoPagoClient).createOrder(
                eq("test-access-token"),
                eq("test-collector"),
                eq("test-pos"),
                any(MercadoPagoRequest.class)
        );

        // Act
        processPaymentWithMercadoPago.processPayment(paymentIntegrationOrder);

        // Assert captured request
        assertThat(capturedRequest.getNotificationUrl()).isEqualTo("http://notification.url");

        verify(mercadoPagoClient).createOrder(
                eq("test-access-token"),
                eq("test-collector"),
                eq("test-pos"),
                any(MercadoPagoRequest.class)
        );
    }
}