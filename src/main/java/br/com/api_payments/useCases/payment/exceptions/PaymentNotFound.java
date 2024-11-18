package br.com.api_payments.useCases.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Payment not found")
public class PaymentNotFound extends RuntimeException {

    public PaymentNotFound() {
        super("Payment not exists");
    }

}
