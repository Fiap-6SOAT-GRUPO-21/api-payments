package br.com.api_payments.application.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Data
@Getter
@Setter
public class PaymentIntegrationResult {

    private UUID paymentId;
    private String qrCode;

}
