package br.com.api_payments.application.controller.config;

import br.com.api_payments.application.config.UrlNotificationMercadoPagoConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UrlMercadoPagoConfigControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UrlNotificationMercadoPagoConfig urlNotificationMercadoPagoConfig;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        UrlMercadoPagoConfigController controller = new UrlMercadoPagoConfigController(urlNotificationMercadoPagoConfig);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class MercadoPagoWebhook {

        @Test
        void shouldReturnCurrentUrl() throws Exception {
            String currentUrl = "https://example.com/order/notifications";
            when(urlNotificationMercadoPagoConfig.getUrl()).thenReturn(currentUrl);

            mockMvc.perform(get("/config/mercado-pago-url"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(currentUrl));

            verify(urlNotificationMercadoPagoConfig, times(1)).getUrl();
        }
    }

    @Nested
    class UpdateUrlConfig {

        @Test
        void shouldUpdateUrlSuccessfully() throws Exception {
            String newBaseUrl = "https://new-url-base.com";
            String expectedUpdatedUrl = newBaseUrl + "/order/notifications";

            mockMvc.perform(put("/config/mercado-pago-url")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newBaseUrl))
                    .andExpect(status().isNoContent());

            verify(urlNotificationMercadoPagoConfig, times(1)).setUrl(expectedUpdatedUrl);
        }
    }

}