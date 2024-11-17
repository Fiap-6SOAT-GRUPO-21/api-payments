package br.com.api_payments.application.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Getter
@Setter
public class PaymentIntegrationOrder {

    private UUID idStore;
    private UUID orderPaymentId;
    private BigDecimal amount;

    private List<PaymentIntegrationItem> items;
}
