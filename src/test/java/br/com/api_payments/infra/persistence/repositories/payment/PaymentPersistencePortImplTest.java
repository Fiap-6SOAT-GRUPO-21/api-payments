package br.com.api_payments.infra.persistence.repositories.payment;

import br.com.api_payments.domain.entity.payment.PaymentDomain;
import br.com.api_payments.infra.persistence.entities.payment.PaymentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentPersistencePortImplTest {

    @Mock
    private PaymentMongoRepository mongoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PaymentPersistencePortImpl paymentPersistencePort;

    private PaymentDomain paymentDomain;
    private PaymentEntity paymentEntity;

    @BeforeEach
    void setUp() {
        paymentDomain = new PaymentDomain();
        paymentDomain.setId(UUID.fromString("2edcf7ae-332f-480f-8a01-f1139f28a2ac"));
        paymentDomain.setAmount(BigDecimal.valueOf(100));

        paymentEntity = new PaymentEntity();
        paymentEntity.setId("2edcf7ae-332f-480f-8a01-f1139f28a2ac");
        paymentEntity.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    void shouldSaveAndReturnPaymentDomain() {
        // Arrange
        when(modelMapper.map(paymentDomain, PaymentEntity.class)).thenReturn(paymentEntity);
        when(mongoRepository.save(paymentEntity)).thenReturn(paymentEntity);
        when(modelMapper.map(paymentEntity, PaymentDomain.class)).thenReturn(paymentDomain);

        // Act
        PaymentDomain result = paymentPersistencePort.save(paymentDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(paymentDomain.getId());
        assertThat(result.getAmount()).isEqualTo(paymentDomain.getAmount());

        verify(modelMapper).map(paymentDomain, PaymentEntity.class);
        verify(mongoRepository).save(paymentEntity);
        verify(modelMapper).map(paymentEntity, PaymentDomain.class);
    }

    @Test
    void findById_shouldReturnPaymentDomainWhenFound() {
        // Arrange
        var idPayment = "2edcf7ae-332f-480f-8a01-f1139f28a2ac";
        when(mongoRepository.findById(idPayment)).thenReturn(Optional.of(paymentEntity));
        when(modelMapper.map(paymentEntity, PaymentDomain.class)).thenReturn(paymentDomain);

        // Act
        Optional<PaymentDomain> result = paymentPersistencePort.findById(idPayment);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(paymentDomain.getId());
        assertThat(result.get().getAmount()).isEqualTo(paymentDomain.getAmount());

        verify(mongoRepository).findById(idPayment);
        verify(modelMapper).map(paymentEntity, PaymentDomain.class);
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() {
        // Arrange
        var noExistentIdPayment = "0a4a9c89-0dba-41a5-8b91-0de08dfac9a3";
        when(mongoRepository.findById(noExistentIdPayment)).thenReturn(Optional.empty());

        // Act
        Optional<PaymentDomain> result = paymentPersistencePort.findById(noExistentIdPayment);

        // Assert
        assertThat(result).isEmpty();

        verify(mongoRepository).findById(noExistentIdPayment);
        verifyNoInteractions(modelMapper);
    }
}