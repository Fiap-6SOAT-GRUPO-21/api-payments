package br.com.api_payments.infra.persistence.entities.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "mercado_pago_gateways")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoGatewayEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String collectors;

    @NotNull
    @Column(nullable = false)
    private String externalPos;
}
