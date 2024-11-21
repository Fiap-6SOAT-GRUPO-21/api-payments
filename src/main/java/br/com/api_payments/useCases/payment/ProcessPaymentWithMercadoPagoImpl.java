package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.config.UrlNotificationMercadoPagoConfig;
import br.com.api_payments.application.dtos.integration.mercadopago.payment.request.CashOutMercadoPago;
import br.com.api_payments.application.dtos.integration.mercadopago.payment.request.ItemMercadoPago;
import br.com.api_payments.application.dtos.integration.mercadopago.payment.request.MercadoPagoRequest;
import br.com.api_payments.application.dtos.integration.mercadopago.payment.response.MercadoPagoResponse;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationResult;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import br.com.api_payments.domain.useCases.payment.ProcessPayment;
import br.com.api_payments.infra.gateways.internal.ApiFood;
import br.com.api_payments.infra.gateways.internal.dto.StoreDomainDTO;
import br.com.api_payments.infra.gateways.payment.MercadoPagoClient;
import br.com.api_payments.useCases.util.DateTimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component(PaymentType.MERCADO_PAGO_QUALIFIER)
public class ProcessPaymentWithMercadoPagoImpl implements ProcessPayment {

    @Autowired
    private UrlNotificationMercadoPagoConfig urlNotificationMercadoPagoConfig;

    @Autowired
    private MercadoPagoClient mercadoPagoClient;

    @Autowired
    private ApiFood apiStore;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${mercado-pago.access-token}")
    private String accessToken;

    @Override
    public PaymentIntegrationResult processPayment(PaymentIntegrationOrder paymentIntegrationOrder) {
        System.out.println("Processing payment with Mercado Pago");

        MercadoPagoRequest mercadoPagoRequest = new MercadoPagoRequest();
        mercadoPagoRequest.setTitle("Tech Challenge");
        mercadoPagoRequest.setDescription("PEDIDOS: " + paymentIntegrationOrder.getOrderPaymentId());
        mercadoPagoRequest.setExpirationDate(DateTimeUtils.generateExpirationDatePayment());
        mercadoPagoRequest.setExternalReference(paymentIntegrationOrder.getOrderPaymentId().toString());
        mercadoPagoRequest.setTotalAmount(paymentIntegrationOrder.getAmount());
        mercadoPagoRequest.setNotificationUrl(urlNotificationMercadoPagoConfig.getUrl());

        paymentIntegrationOrder.getItems().forEach(item -> {
            ItemMercadoPago itemMercadoPago = new ItemMercadoPago();
            itemMercadoPago.setQuantity(item.getQuantity());
            itemMercadoPago.setTotalAmount(item.getTotalAmount());
            itemMercadoPago.setTitle(item.getName());
            itemMercadoPago.setUnitPrice(item.getUnitPrice());
            itemMercadoPago.setUnitMeasure("unit");
            mercadoPagoRequest.getItems().add(itemMercadoPago);
        });

        StoreDomainDTO storeDomainDTO = apiStore.findStoreById(paymentIntegrationOrder.getIdStore());

        MercadoPagoResponse paymentMercadoPagoResponse = mercadoPagoClient.createOrder(
                accessToken,
                storeDomainDTO.getMercadoPagoGateway().getCollectors(),
                storeDomainDTO.getMercadoPagoGateway().getExternalPos(),
                mercadoPagoRequest);

        return new PaymentIntegrationResult(paymentIntegrationOrder.getOrderPaymentId(),
                paymentMercadoPagoResponse.getQrData());
    }
}
