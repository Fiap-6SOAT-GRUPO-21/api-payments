package br.com.api_payments.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class UrlNotificationMercadoPagoConfig {

    @Value("${mercado-pago.notification-url}")
    private String url;

}
