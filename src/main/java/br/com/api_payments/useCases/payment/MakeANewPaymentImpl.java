package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.payment.PaymentIntegrationItem;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationOrder;
import br.com.api_payments.application.dtos.payment.PaymentIntegrationResult;
import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import br.com.api_payments.domain.persistence.payment.PaymentPersistence;
import br.com.api_payments.domain.useCases.payment.MakeANewPayment;
import br.com.api_payments.domain.useCases.payment.ProcessPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeANewPaymentImpl implements MakeANewPayment {

    final PaymentPersistence paymentPersistence;
    @Override
    public PaymentDomain execute(OrderDTO orderDomain, PaymentType provider, ProcessPayment processPayment) {

        List<PaymentIntegrationItem> item = new ArrayList<>();

        orderDomain.getItems().forEach(itemDomain -> item.add(new PaymentIntegrationItem(
                itemDomain.getQuantity(),
                itemDomain.getProductPrice().multiply(BigDecimal.valueOf(itemDomain.getQuantity())),
                itemDomain.getProductPrice(),
                itemDomain.getProductName()
        )));


        PaymentIntegrationOrder paymentIntegrationOrder = new PaymentIntegrationOrder(
                orderDomain.getIdStore(),
                UUID.randomUUID(),
                orderDomain.getTotal(),
                item
        );

        PaymentIntegrationResult paymentIntegrationResult = processPayment.processPayment(paymentIntegrationOrder);

        PaymentDomain paymentDomain = new PaymentDomain();
        paymentDomain.setPaymentId(paymentIntegrationResult.getPaymentId());
        paymentDomain.setAmount(paymentIntegrationOrder.getAmount());
        paymentDomain.setQrCode(paymentIntegrationResult.getQrCode());
        paymentDomain.setType(provider);
        paymentDomain.setStatus(PaymentStatus.PENDING);
        paymentDomain.setIdOrder(orderDomain.getOrderId());

        return paymentPersistence.save(paymentDomain);
    }
}
