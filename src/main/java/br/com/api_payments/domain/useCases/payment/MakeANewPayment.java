package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;

public interface MakeANewPayment {

    PaymentDomain execute(OrderDTO orderDomain, PaymentType provider, ProcessPayment processPayment);
}
