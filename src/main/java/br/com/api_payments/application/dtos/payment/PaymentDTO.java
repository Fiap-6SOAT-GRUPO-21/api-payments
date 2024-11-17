package br.com.api_payments.application.dtos.payment;

import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDTO {

    @Schema(description = "Payment ID", example = "5824b024-e0f7-4eea-a972-dbfd30bb71f3")
    private UUID paymentId;

    @Schema(description = "Order Id", example = "5824b024-e0f7-4eea-a972-dbfd30bb71f3")
    @NotNull
    private UUID idOrder;

    @Schema(description = "Total amount of the order", example = "30.00")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "Payment type", example = "MERCADO_PAGO")
    @NotNull
    private PaymentType type;

    @Schema(description = "QrCode to pay", example = "")
    @NotNull
    private String qrCode;

    @Schema(description = "Payment Status", example = "PENDING")
    @NotNull
    private PaymentStatus status;
}
