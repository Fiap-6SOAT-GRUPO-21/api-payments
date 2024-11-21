package br.com.api_payments.infra.persistence.entities.payment;

import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collation = "payments")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentEntity {

    @Id
    @Setter
    private String id;

    @NotNull
    @Setter
    private BigDecimal amount;

    @NotNull
    private String qrCode;

    @NotNull
    private PaymentType type;

    @NotNull
    private PaymentStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private UUID idOrder;
}
