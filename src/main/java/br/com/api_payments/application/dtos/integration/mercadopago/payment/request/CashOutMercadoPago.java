package br.com.api_payments.application.dtos.integration.mercadopago.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashOutMercadoPago {
    private BigDecimal amount;
}
