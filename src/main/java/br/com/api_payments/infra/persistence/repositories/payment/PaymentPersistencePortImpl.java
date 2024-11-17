package br.com.api_payments.infra.persistence.repositories.payment;


import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.persistence.payment.PaymentPersistence;
import br.com.api_payments.infra.persistence.entities.payment.PaymentEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class PaymentPersistencePortImpl implements PaymentPersistence {

    private final PaymentJpaRepository paymentJpaRepository;
    private final ModelMapper modelMapper;

    @Override
    public PaymentDomain save(PaymentDomain paymentDomain) {
        PaymentEntity map = modelMapper.map(paymentDomain, PaymentEntity.class);
        PaymentEntity paymentEntity = paymentJpaRepository.save(map);
        return modelMapper.map(paymentEntity, PaymentDomain.class);
    }

    @Override
    public Optional<PaymentDomain> findById(UUID idPayment) {
        return paymentJpaRepository.findById(idPayment)
                .map(paymentEntity -> modelMapper.map(paymentEntity, PaymentDomain.class));
    }
}
