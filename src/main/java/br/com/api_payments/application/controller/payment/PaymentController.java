package br.com.api_payments.application.controller.payment;

import br.com.api_payments.application.dtos.payment.NewPaymentDTO;
import br.com.api_payments.application.dtos.payment.PaymentDTO;
import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.domain.useCases.payment.FindPaymentById;
import br.com.api_payments.domain.useCases.payment.MakeANewPayment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PaymentController {

    private final FindPaymentById findPaymentById;
    private final MakeANewPayment newPayment;
    private final ModelMapper mapper;

    @Operation(summary = "Make a new payment")
    @PostMapping
    public ResponseEntity<PaymentDTO> makeANewPayment(@RequestBody NewPaymentDTO newPaymentDTO) {


        var payment = newPayment.execute(newPaymentDTO.getOrderDTO(), newPaymentDTO.getProvider(), null);
        var paymentDTO = mapper.map(payment, PaymentDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentDTO);
    }

    @Operation(summary = "Make a new payment")
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> findPaymentById(@PathVariable("paymentId") UUID paymentId) {

        var payment = findPaymentById.execute(paymentId);
        var paymentDTO = mapper.map(payment, PaymentDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentDTO);
    }


    @GetMapping(value = "/qr-code/{idPayment}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<ByteArrayResource> generateQRCode(@PathVariable UUID idPayment) {
        try {

            PaymentDomain paymentDomain = findPaymentById.execute(idPayment);

            int width = 300;
            int height = 300;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = qrCodeWriter.encode(paymentDomain.getQrCode(), BarcodeFormat.QR_CODE, width, height, hints);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            ByteArrayResource byteArrayResource = new ByteArrayResource(pngData);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(byteArrayResource);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}