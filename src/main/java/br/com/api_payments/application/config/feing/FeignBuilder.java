package br.com.api_payments.application.config.feing;

import feign.Feign;
import feign.Logger;
import feign.Request;


public abstract class FeignBuilder {

    protected Feign.Builder feignBuilder() {
        Request.Options defaultOpts = new Request.Options();
        return Feign.builder()
                .logger(customFeignLogger())
                .logLevel(Logger.Level.FULL)
                .options(new Request.Options(
                        defaultOpts.connectTimeoutMillis(),
                        defaultOpts.connectTimeoutUnit(),
                        defaultOpts.readTimeout(),
                        defaultOpts.readTimeoutUnit(),
                        false
                ));
    }

    private CustomFeignLogger customFeignLogger() {
        return new CustomFeignLogger();
    }
}
