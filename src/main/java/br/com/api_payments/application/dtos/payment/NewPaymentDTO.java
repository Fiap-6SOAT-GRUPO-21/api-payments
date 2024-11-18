package br.com.api_payments.application.dtos.payment;

import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPaymentDTO {

    private OrderDTO orderDTO;
    private PaymentType provider;
}
