package br.com.api_payments.domain.persistence.payment;

import br.com.api_payments.domain.entity.payment.MercadoPagoGatewayDomain;

import java.util.Optional;
import java.util.UUID;

public interface MercadoPagoGatewayPersistence {

    Optional<MercadoPagoGatewayDomain> findById(UUID id);

    MercadoPagoGatewayDomain save(MercadoPagoGatewayDomain mercadoPagoGatewayDomain);
}
