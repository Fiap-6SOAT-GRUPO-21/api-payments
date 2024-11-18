package br.com.api_payments.application.dtos.integration.mercadopago.payment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class MercadoPagoResponse {

    @JsonProperty("qr_data")
    private String qrData;

    @JsonProperty("in_store_order_id")
    private String inStoreOrderId;

}