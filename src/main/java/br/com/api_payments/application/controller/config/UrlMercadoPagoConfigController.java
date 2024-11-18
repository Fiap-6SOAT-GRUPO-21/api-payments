package br.com.api_payments.application.controller.config;

import br.com.api_payments.application.config.UrlNotificationMercadoPagoConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config/mercado-pago-url")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UrlMercadoPagoConfigController {

    private UrlNotificationMercadoPagoConfig urlNotificationMercadoPagoConfig;

    @Operation(summary = "Get url base application utilization for webhook mercado pago")
    @GetMapping
    public ResponseEntity<String> getUrlNotificationMercadoPagoConfig() {
        return ResponseEntity.ok(urlNotificationMercadoPagoConfig.getUrl());
    }

    @Operation(summary = "Update url base application utilization for webhook mercado pago")
    @PutMapping
    public ResponseEntity<Void> updateUrlConfig(
            @RequestBody
            @Schema(description = "Base url application", example = "https://minha-url-base.com")
            String newUrlBase) {
        urlNotificationMercadoPagoConfig.setUrl(newUrlBase + "/order/notifications");
        return ResponseEntity.noContent().build();
    }
}
