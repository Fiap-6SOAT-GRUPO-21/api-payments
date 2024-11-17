package br.com.api_payments.domain.entity.payment.enums;

public enum PaymentStatus {

    PENDING,
    APPROVED,
    CANCELLED;

    public static PaymentStatus fromString(String status) {
        return switch (status) {
            case "opened" -> PENDING;
            case "closed" -> APPROVED;
            case "expired" -> CANCELLED;
            default -> throw new IllegalArgumentException("Invalid payment status: " + status);
        };
    }

}
