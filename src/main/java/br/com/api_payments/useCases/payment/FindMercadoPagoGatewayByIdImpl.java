package br.com.api_payments.useCases.payment;

import br.com.api_payments.useCases.payment.exceptions.MercadoPagoGatewayNotFound;
import br.com.api_payments.domain.entity.DomainEntity;
import br.com.api_payments.domain.persistence.payment.MercadoPagoGatewayPersistence;
import br.com.api_payments.domain.useCases.payment.FindMercadoPagoGatewayById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindMercadoPagoGatewayByIdImpl implements FindMercadoPagoGatewayById {

    private final MercadoPagoGatewayPersistence mercadoPagoGatewayPersistence;

    @Override
    public DomainEntity execute(UUID id) {
        return mercadoPagoGatewayPersistence.findById(id)
                .orElseThrow(MercadoPagoGatewayNotFound::new);
    }
}
