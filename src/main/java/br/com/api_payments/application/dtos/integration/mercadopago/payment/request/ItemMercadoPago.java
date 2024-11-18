package br.com.api_payments.application.dtos.integration.mercadopago.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemMercadoPago {
    @JsonProperty("sku_number")
    private String skuNumber;

    private String category;

    private String title;

    private String description;

    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    private int quantity;

    @JsonProperty("unit_measure")
    private String unitMeasure;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
}
