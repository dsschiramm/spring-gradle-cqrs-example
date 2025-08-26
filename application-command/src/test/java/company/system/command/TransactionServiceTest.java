package company.system.command;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.transaction.OutOfFundsException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.models.TransactionDO;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.domain.services.TransactionService;
import company.system.command.repositories.CardholderRepository;
import company.system.command.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardholderRepository cardholderRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("transact success")
    void transactSuccess() throws DomainException {

        String originDocument = "22040998055";
        String destinationDocument = "88823598087";
        BigDecimal amount = new BigDecimal("10.00");

        when(cardholderRepository.findCardholderIdByDocument(originDocument)).thenReturn(1L);
        when(cardholderRepository.findCardholderIdByDocument(destinationDocument)).thenReturn(2L);
        when(transactionRepository.findAllByCardholder(1L)).thenReturn(List.of(new TransactionDO(UUID.randomUUID(), amount, 1l)));

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
    void outOfFundsTest() throws DomainException {

        String originDocument = "20355090090";
        String destinationDocument = "52281915000";
        BigDecimal amount = new BigDecimal("1000.00");

        when(cardholderRepository.findCardholderIdByDocument(originDocument)).thenReturn(1L);
        when(cardholderRepository.findCardholderIdByDocument(destinationDocument)).thenReturn(2L);

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        assertThrows(OutOfFundsException.class, () -> transactionService.transact(request));
    }

    @Test
    @DisplayName("cardholder not found when try transact")
    void cardholderNotFoundTest() {

        String originDocument = "47603380049";
        String destinationDocument = "67499918076";
        BigDecimal amount = new BigDecimal("75.00");

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        when(cardholderRepository.findCardholderIdByDocument(originDocument)).thenReturn(null);
        when(cardholderRepository.findCardholderIdByDocument(destinationDocument)).thenReturn(null);

        assertThrows(CardholderNotFoundException.class, () -> transactionService.transact(request));
    }
}