package br.com.api_payments.useCases.payment;

import br.com.api_payments.domain.entity.DomainEntity;
import br.com.api_payments.domain.entity.payment.MercadoPagoGatewayDomain;
import br.com.api_payments.domain.persistence.payment.MercadoPagoGatewayPersistence;
import br.com.api_payments.domain.useCases.payment.CreateNewMercadoPagoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewMercadoPagoGatewayImpl implements CreateNewMercadoPagoGateway {

    private final MercadoPagoGatewayPersistence mercadoPagoGatewayPersistence;
    @Override
    public DomainEntity execute(MercadoPagoGatewayDomain mercadoPagoGatewayDomain) {
        return mercadoPagoGatewayPersistence.save(mercadoPagoGatewayDomain);
    }
}
