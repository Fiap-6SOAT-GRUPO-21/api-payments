package br.com.api_payments.application.controller.notification;

import br.com.api_payments.domain.useCases.payment.UpdatePaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NotificationController {

    private final UpdatePaymentStatus updatePaymentStatus;

    @PostMapping("/notifications")
    public ResponseEntity<Void> receiveNotification(
            @RequestParam("id") Long id,
            @RequestParam("topic") String topic,
            @RequestBody String payload) {
        log.debug("Received notification - ID: {}, Topic: {}", id, topic);
        log.debug("Payload: {}", payload);

        if (topic.equals("merchant_order"))
            updatePaymentStatus.execute(id);

        return ResponseEntity.ok().build();
    }
}
