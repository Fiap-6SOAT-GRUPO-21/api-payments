package br.com.api_payments.infra.gateways.internal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreDomainDTO {

    private boolean active = true;
    private MercadoPagoGatewayDTO mercadoPagoGateway;
}
