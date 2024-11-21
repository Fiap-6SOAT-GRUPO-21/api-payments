package br.com.api_payments.application.controller.notification;

import br.com.api_payments.domain.useCases.payment.UpdatePaymentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UpdatePaymentStatus updatePaymentStatus;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        NotificationController notificationController = new NotificationController(updatePaymentStatus);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Notification {

        @Test
        void shouldProcessMerchantOrderNotification() throws Exception {
            Long id = 12345L;
            String topic = "merchant_order";
            String payload = "{\"details\":\"test\"}";

            mockMvc.perform(post("/order/notifications")
                            .param("id", id.toString())
                            .param("topic", topic)
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(updatePaymentStatus, times(1)).execute(id);
        }

        @Test
        void shouldIgnoreNonMerchantOrderNotification() throws Exception {
            Long id = 12345L;
            String topic = "other_topic";
            String payload = "{\"details\":\"test\"}";

            mockMvc.perform(post("/order/notifications")
                            .param("id", id.toString())
                            .param("topic", topic)
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(updatePaymentStatus, never()).execute(anyLong());
        }
    }
}