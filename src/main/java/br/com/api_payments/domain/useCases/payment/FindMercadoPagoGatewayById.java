package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.domain.entity.DomainEntity;

import java.util.UUID;

public interface FindMercadoPagoGatewayById {

    DomainEntity execute(UUID id);
}
