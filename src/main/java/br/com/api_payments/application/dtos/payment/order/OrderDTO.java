package br.com.api_payments.application.dtos.payment.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private UUID orderId;
    private List<OrderItemDTO> items;
    private BigDecimal total;
    private UUID idStore;
}
