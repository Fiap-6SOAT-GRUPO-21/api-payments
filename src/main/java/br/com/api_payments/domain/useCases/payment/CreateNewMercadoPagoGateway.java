package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.domain.entity.DomainEntity;
import br.com.api_payments.infra.gateways.internal.dto.MercadoPagoGatewayDTO;

public interface CreateNewMercadoPagoGateway {

    DomainEntity execute(MercadoPagoGatewayDTO mercadoPagoGatewayDTO);
}
