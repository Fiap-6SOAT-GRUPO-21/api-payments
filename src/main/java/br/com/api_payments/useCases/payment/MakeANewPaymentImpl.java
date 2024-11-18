package br.com.api_payments.useCases.payment;

import br.com.api_payments.application.dtos.payment.NewPaymentDTO;
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
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeANewPaymentImpl implements MakeANewPayment {

    final PaymentPersistence paymentPersistence;

    private final Map<String, ProcessPayment> processPaymentList;

    @Override
    public PaymentDomain execute(NewPaymentDTO newPaymentDTO) {

        List<PaymentIntegrationItem> item = new ArrayList<>();

        newPaymentDTO.getOrderDTO().getItems().forEach(itemDomain -> item.add(new PaymentIntegrationItem(
                itemDomain.getQuantity(),
                itemDomain.getProductPrice().multiply(BigDecimal.valueOf(itemDomain.getQuantity())),
                itemDomain.getProductPrice(),
                itemDomain.getProductName()
        )));


        PaymentIntegrationOrder paymentIntegrationOrder = new PaymentIntegrationOrder(
                newPaymentDTO.getOrderDTO().getIdStore(),
                UUID.randomUUID(),
                newPaymentDTO.getOrderDTO().getTotal(),
                item
        );

        ProcessPayment processPayment = processPaymentList.get(newPaymentDTO.getProvider().name());
        if (processPayment == null)
            throw new IllegalArgumentException("Invalid payment provider: " + newPaymentDTO.getProvider());

        PaymentIntegrationResult paymentIntegrationResult = processPayment.processPayment(paymentIntegrationOrder);

        PaymentDomain paymentDomain = new PaymentDomain();
        paymentDomain.setId(paymentIntegrationResult.getPaymentId());
        paymentDomain.setAmount(paymentIntegrationOrder.getAmount());
        paymentDomain.setQrCode(paymentIntegrationResult.getQrCode());
        paymentDomain.setType(newPaymentDTO.getProvider());
        paymentDomain.setStatus(PaymentStatus.PENDING);
        paymentDomain.setIdOrder(newPaymentDTO.getOrderDTO().getOrderId());

        return paymentPersistence.save(paymentDomain);
    }
}
