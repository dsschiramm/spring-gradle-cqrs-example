package company.system.command;

import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.transaction.MerchantTransferException;
import company.system.command.domain.exceptions.transaction.OutOfFundsException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.models.CardholderDO;
import company.system.command.domain.models.TransactionDO;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.domain.services.TransactionService;
import company.system.command.repositories.CardholderRepository;
import company.system.command.repositories.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardholderRepository cardholderRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("transact success")
    void transactSuccess() throws DomainException {

        String originDocument = "22040998055";
        String destinationDocument = "88823598087";
        BigDecimal amount = new BigDecimal("10.00");

        when(cardholderRepository.findCardholderByDocument(originDocument)).thenReturn(
                new CardholderDO(UUID.randomUUID(), "aaa", "aaa@aaa.com", "22040998055", "1234", CardholderTypeEnum.PERSON, 1L));

        when(cardholderRepository.findCardholderByDocument(destinationDocument)).thenReturn(
                new CardholderDO(UUID.randomUUID(), "bbb", "bbb@bbb.com", "88823598087", "4321", CardholderTypeEnum.PERSON, 2L));

        when(transactionRepository.findAllByCardholder(1L)).thenReturn(List.of(new TransactionDO(UUID.randomUUID(), amount, 1L)));

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        UUID operationId = transactionService.transact(request);

        assertNotNull(operationId);

        ArgumentCaptor<List<TransactionDO>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(transactionRepository).save(captor.capture());

        List<TransactionDO> savedList = captor.getValue();

        Optional<TransactionDO> debt = savedList.stream().filter(transaction -> transaction.getCardholderId() == 1L).findFirst();
        Optional<TransactionDO> credit = savedList.stream().filter(transaction -> transaction.getCardholderId() == 2L).findFirst();

        assertTrue(debt.isPresent());
        assertTrue(credit.isPresent());
        assertEquals(debt.get().getValor(), amount.negate());
        assertEquals(credit.get().getValor(), amount);
    }

    @Test
    @DisplayName("credit success")
    void creditSuccess() {
        UUID operationId = UUID.randomUUID();
        Long cardholderId = 123L;
        BigDecimal valor = new BigDecimal("50.00");

        transactionService.credit(operationId, cardholderId, valor);

        ArgumentCaptor<TransactionDO> captor = ArgumentCaptor.forClass(TransactionDO.class);
        verify(transactionRepository).save(captor.capture());

        TransactionDO saved = captor.getValue();
        assertEquals(operationId, saved.getOperationId());
        assertEquals(cardholderId, saved.getCardholderId());
        assertEquals(valor, saved.getValor());
    }

    @Test
    @DisplayName("throw OutOfFundsException when try transact without funds")
    void outOfFundsTest() {

        String originDocument = "20355090090";
        String destinationDocument = "52281915000";
        BigDecimal amount = new BigDecimal("1000.00");

        when(cardholderRepository.findCardholderByDocument(originDocument)).thenReturn(
                new CardholderDO(UUID.randomUUID(), "bbb", "bbb@bbb.com", "20355090090", "4321", CardholderTypeEnum.PERSON, 1L));

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        assertThrows(OutOfFundsException.class, () -> transactionService.transact(request));
    }

    @Test
    @DisplayName("throw MerchantTransferException when try transact with Merchant")
    void merchantTransferTest() {

        String originDocument = "20355090090";
        String destinationDocument = "52281915000";
        BigDecimal amount = new BigDecimal("1000.00");

        when(cardholderRepository.findCardholderByDocument(originDocument)).thenReturn(
                new CardholderDO(UUID.randomUUID(), "bbb", "bbb@bbb.com", "20355090090", "4321", CardholderTypeEnum.MERCHANT, 1L));

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        assertThrows(MerchantTransferException.class, () -> transactionService.transact(request));
    }

    @Test
    @DisplayName("cardholder not found when try transact")
    void cardholderNotFoundTest() {

        String originDocument = "47603380049";
        String destinationDocument = "67499918076";
        BigDecimal amount = new BigDecimal("75.00");

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        when(cardholderRepository.findCardholderByDocument(originDocument)).thenReturn(null);

        assertThrows(CardholderNotFoundException.class, () -> transactionService.transact(request));
    }
}