package br.com.api_payments.domain.useCases.payment;

public interface UpdatePaymentStatus {

    void execute(Long merchantOrderId);
}
