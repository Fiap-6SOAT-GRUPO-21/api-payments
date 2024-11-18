package br.com.api_payments.infra.gateways.internal;


import br.com.api_payments.infra.gateways.internal.dto.StatusOrder;
import br.com.api_payments.infra.interceptor.DefaultInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "internal-api-order",
        url = "${api.url.api-order}",
        configuration = DefaultInterceptor.class
)
public interface ApiOrder {

    @PutMapping("/order/status/{id}")
    void updateOrderStatus(@PathVariable("id") UUID id, @RequestParam("status") StatusOrder status);
}
