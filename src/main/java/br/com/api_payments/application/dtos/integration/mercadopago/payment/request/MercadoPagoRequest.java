package br.com.api_payments.application.dtos.integration.mercadopago.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MercadoPagoRequest {
    private String description;

    @JsonProperty("expiration_date")
    private String expirationDate;

    @JsonProperty("external_reference")
    private String externalReference;

    private List<ItemMercadoPago> items = new ArrayList<>();

    @JsonProperty("notification_url")
    private String notificationUrl;

    private String title;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
}
