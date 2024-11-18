package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.application.dtos.payment.NewPaymentDTO;
import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;

public interface MakeANewPayment {

    PaymentDomain execute(NewPaymentDTO newPaymentDTO);
}
