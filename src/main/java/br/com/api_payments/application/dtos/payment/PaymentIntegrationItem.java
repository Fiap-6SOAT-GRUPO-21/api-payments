package br.com.api_payments.application.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Getter
@Setter
public class PaymentIntegrationItem {

    private int quantity;

    private BigDecimal totalAmount;

    private BigDecimal unitPrice;

    private String name;

}
