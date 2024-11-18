package br.com.api_payments.infra.gateways.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDomainDTO {

    private UUID id;
    private String name;
    private boolean active = true;
    private UUID idMercadoPagoGateway;
    private MercadoPagoGatewayDTO mercadoPagoGateway;
}
