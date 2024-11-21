package br.com.api_payments.application.controller.payment;

import br.com.api_payments.application.dtos.payment.NewPaymentDTO;
import br.com.api_payments.application.dtos.payment.PaymentDTO;
import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.useCases.payment.FindPaymentById;
import br.com.api_payments.domain.useCases.payment.MakeANewPayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FindPaymentById findPaymentById;

    @Mock
    private MakeANewPayment makeANewPayment;

    @Mock
    private ModelMapper mapper;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PaymentController paymentController = new PaymentController(findPaymentById, makeANewPayment, mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CreateAPayment {

        @Test
        void shouldMakeNewPaymentWithSuccess() throws Exception {

            // Arrange
            NewPaymentDTO newPaymentDTO = new NewPaymentDTO();
            newPaymentDTO.setOrderDTO(new OrderDTO());

            PaymentDomain paymentDomain = new PaymentDomain();
            paymentDomain.setId(UUID.fromString("8d1593c6-8953-4c1b-9aac-ed7ffc1ac8b0"));
            paymentDomain.setAmount(BigDecimal.valueOf(100));

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(UUID.fromString("8d1593c6-8953-4c1b-9aac-ed7ffc1ac8b0"));
            paymentDTO.setAmount(BigDecimal.valueOf(100));

            when(makeANewPayment.execute(any(NewPaymentDTO.class))).thenReturn(paymentDomain);
            when(mapper.map(any(PaymentDomain.class), ArgumentMatchers.eq(PaymentDTO.class))).thenReturn(paymentDTO);

            // Act & Assert
            mockMvc.perform(post("/payment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(newPaymentDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value("8d1593c6-8953-4c1b-9aac-ed7ffc1ac8b0"))
                    .andExpect(jsonPath("$.amount").value(100));

            verify(makeANewPayment, times(1)).execute(any(NewPaymentDTO.class));
            verify(mapper, times(1)).map(any(PaymentDomain.class), ArgumentMatchers.eq(PaymentDTO.class));
        }
    }

    @Nested
    class FindPayment {

        @Test
        void shouldReturnPaymentByIdWithSuccess() throws Exception {
            // Arrange
            UUID paymentId = UUID.fromString("fbf5aef7-5471-4b70-932d-7e9887b06784");

            PaymentDomain paymentDomain = new PaymentDomain();
            paymentDomain.setId(UUID.fromString("fbf5aef7-5471-4b70-932d-7e9887b06784"));
            paymentDomain.setAmount(BigDecimal.valueOf(200));

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(UUID.fromString("fbf5aef7-5471-4b70-932d-7e9887b06784"));
            paymentDTO.setAmount(BigDecimal.valueOf(200));

            when(findPaymentById.execute(eq("fbf5aef7-5471-4b70-932d-7e9887b06784"))).thenReturn(paymentDomain);
            when(mapper.map(any(PaymentDomain.class), eq(PaymentDTO.class))).thenReturn(paymentDTO);

            // Act & Assert
            mockMvc.perform(get("/payment/{paymentId}", paymentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("fbf5aef7-5471-4b70-932d-7e9887b06784"))
                    .andExpect(jsonPath("$.amount").value(200));

            verify(findPaymentById, times(1)).execute(eq("fbf5aef7-5471-4b70-932d-7e9887b06784"));
            verify(mapper, times(1)).map(any(PaymentDomain.class), eq(PaymentDTO.class));
        }
    }

    @Nested
    class GenerateQRCode {

        @Test
        void shouldGenerateANewQrCodeWithSuccess() throws Exception {
            // Arrange
            String idPayment = "12345";

            PaymentDomain paymentDomain = new PaymentDomain();
            paymentDomain.setQrCode("https://example.com/payment/12345");

            when(findPaymentById.execute(eq(idPayment))).thenReturn(paymentDomain);

            // Act & Assert
            mockMvc.perform(get("/payment/qr-code/{idPayment}", idPayment)
                            .contentType(MediaType.IMAGE_PNG_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", containsString("image/png")));

            verify(findPaymentById, times(1)).execute(eq(idPayment));
        }
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}