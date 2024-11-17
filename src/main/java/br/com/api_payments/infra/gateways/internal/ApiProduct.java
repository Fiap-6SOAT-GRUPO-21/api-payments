package br.com.api_payments.infra.gateways.internal;


import br.com.api_payments.infra.interceptor.DefaultInterceptor;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "internal-api-food",
        url = "${api.url.api-food.product}",
        configuration = DefaultInterceptor.class
)
public interface ApiProduct {
}
