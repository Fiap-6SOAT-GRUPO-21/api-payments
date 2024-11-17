package br.com.api_payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPaymentsApplication.class, args);
    }

}
