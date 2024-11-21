package br.com.api_payments.infra.interceptor;


import br.com.api_payments.application.config.feing.FeignBuilder;
import feign.Feign;
import org.springframework.context.annotation.Bean;

public class DefaultInterceptor extends FeignBuilder {

    @Bean
    public Feign.Builder builder() {
        return super.feignBuilder();
    }
}
