package br.com.api_payments.bdd;

import br.com.api_payments.application.dtos.payment.NewPaymentDTO;
import br.com.api_payments.application.dtos.payment.PaymentDTO;
import br.com.api_payments.application.dtos.payment.order.OrderDTO;
import br.com.api_payments.application.dtos.payment.order.OrderItemDTO;
import br.com.api_payments.domain.entity.payment.enums.PaymentStatus;
import br.com.api_payments.domain.entity.payment.enums.PaymentType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PaymentStepDefinitions {

    private Response response;
    private PaymentDTO paymentResponse;
    private final String ENDPOINT = "http://localhost:8090/payment";

    @When("the order details and the payment provider are received")
    public PaymentDTO orderDetailsAndProviderAreReceived() {
        var orderId = UUID.fromString("deca4e1f-7cfc-4226-a306-0c845e92f216");
        var storeId = UUID.fromString("a757f63e-d859-4cdd-a78a-2776ab47a7c1");
        var item = new OrderItemDTO(UUID.randomUUID(), "Product A", BigDecimal.valueOf(50), orderId, 2);
        var orderDTO = new OrderDTO(orderId, List.of(item), BigDecimal.valueOf(100), storeId);
        var request = new NewPaymentDTO(orderDTO, PaymentType.MERCADO_PAGO);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(ENDPOINT);
        return response.then().extract().as(PaymentDTO.class);
    }

    @Then("the payment is successfully processed")
    public void thePaymentIsSuccessfullyProcessed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/paymentResponse.json"));
    }

    @Given("a payment has already been requested")
    public void aPaymentHasAlreadyBeenRequested() {
        var paymentId = UUID.fromString("5d0ddc61-25d1-4fc3-9dc6-7033f26eb6cd");
        var orderId = UUID.fromString("deca4e1f-7cfc-4226-a306-0c845e92f216");
        paymentResponse = new PaymentDTO(paymentId, orderId, BigDecimal.valueOf(100), PaymentType.MERCADO_PAGO, null, PaymentStatus.PENDING);
    }

    @When("the payment identifier is received")
    public void thePaymentIdentifierIsReceived() {
        var paymentId = UUID.fromString("5d0ddc61-25d1-4fc3-9dc6-7033f26eb6cd");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/payment/{paymentId}", paymentId.toString());
    }

    @Then("the payment details are returned")
    public void thePaymentDetailsAreReturned() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(matchesJsonSchemaInClasspath("./schemas/paymentResponse.json"));
    }
}
