package br.com.api_payments.infra.gateways.internal.dto;


import br.com.api_payments.domain.entity.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MercadoPagoGatewayDTO extends DomainEntity {

    private String collectors;
    private String externalPos;
}
