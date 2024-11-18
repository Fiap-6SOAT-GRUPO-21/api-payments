package br.com.api_payments.infra.gateways.internal;


import br.com.api_payments.infra.gateways.internal.dto.StoreDomainDTO;
import br.com.api_payments.infra.interceptor.DefaultInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "internal-api-food",
        url = "${api.url.api-food}",
        configuration = DefaultInterceptor.class
)
public interface ApiFood {

    @GetMapping("store/{storeId}")
    StoreDomainDTO findStoreById(@PathVariable("storeId") UUID storeId);
}
