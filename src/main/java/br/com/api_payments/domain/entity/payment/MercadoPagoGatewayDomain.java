package br.com.api_payments.domain.entity.payment;


import br.com.api_payments.domain.entity.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MercadoPagoGatewayDomain extends DomainEntity {

    private String collectors;
    private String externalPos;
}
