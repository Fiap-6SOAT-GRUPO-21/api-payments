package br.com.api_payments.infra.persistence.repositories.payment;

import br.com.api_payments.infra.persistence.entities.payment.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentMongoRepository extends MongoRepository<PaymentEntity, String> {

}
