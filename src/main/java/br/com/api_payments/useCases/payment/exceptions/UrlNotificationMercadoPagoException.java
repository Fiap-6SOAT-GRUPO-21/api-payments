package br.com.api_payments.useCases.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Url notification not implemented")
public class UrlNotificationMercadoPagoException extends RuntimeException {

    public UrlNotificationMercadoPagoException() {
        super("Url notification not implemented");
    }

}
