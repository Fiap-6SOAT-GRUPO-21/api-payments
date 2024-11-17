package br.com.api_payments.domain.useCases.payment;

import br.com.api_payments.domain.entity.DomainEntity;
import br.com.api_payments.domain.entity.payment.MercadoPagoGatewayDomain;

public interface CreateNewMercadoPagoGateway {

    DomainEntity execute(MercadoPagoGatewayDomain mercadoPagoGatewayDomain);
}
