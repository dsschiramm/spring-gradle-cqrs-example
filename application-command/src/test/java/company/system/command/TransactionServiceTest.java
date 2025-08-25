package company.system.command;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.transaction.OutOfFundsException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.models.TransactionDO;
import company.system.command.domain.requests.CardholderRequest;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.domain.services.CardholderService;
import company.system.command.domain.services.TransactionService;
import company.system.command.repositories.CardholderRepository;
import company.system.command.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardholderRepository cardholderRepository;

    @InjectMocks
    private TransactionService transactionService;

    @InjectMocks
    private CardholderService cardholderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("transact success")
    void transactSuccess() throws DomainException {

        String originDocument = "20355090090";
        String destinationDocument = "52281915000";
        BigDecimal amount = new BigDecimal("1000.00");

        Long origem = cardholderRepository.findCardholderIdByDocument(originDocument);

        if (origem == null) {
            origem = cardholderService.create(new CardholderRequest("aaa", "aaa@aaa.com", "12345678900", "1234"));
        }

        Long destination = cardholderRepository.findCardholderIdByDocument(originDocument);

        if (destination == null) {
            destination = cardholderService.create(new CardholderRequest("bbb", "bbb@bbb.com", "00987654321", "1234"));
        }

        assertNotNull(origem);
        assertNotNull(destination);

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        UUID operationId = transactionService.transact(request);

        assertNotNull(operationId);

        List<TransactionDO> historyOrigem = transactionRepository.findAllByCardholder(origem);
        List<TransactionDO> historyDestination = transactionRepository.findAllByCardholder(destination);

        Optional<TransactionDO> debt = historyOrigem.stream().filter(transaction -> transaction.getOperationId() == operationId).findFirst();
        Optional<TransactionDO> credit = historyDestination.stream().filter(transaction -> transaction.getOperationId() == operationId).findFirst();

        assertTrue(debt.isPresent());
        assertTrue(credit.isPresent());
        assertEquals(debt.get().getValor(), amount.negate());
        assertEquals(credit.get().getValor(), amount);
    }

    @Test
    @DisplayName("throw OutOfFundsException when try transact without funds")
    void outOfFundsTest() throws DomainException {

        String originDocument = "20355090090";
        String destinationDocument = "52281915000";
        BigDecimal amount = new BigDecimal("1000.00");

        Long origem = cardholderRepository.findCardholderIdByDocument(originDocument);

        if (origem == null) {
            origem = cardholderService.create(new CardholderRequest("aaa", "aaa@aaa.com", "12345678900", "1234"));
        }

        Long destination = cardholderRepository.findCardholderIdByDocument(originDocument);

        if (destination == null) {
            destination = cardholderService.create(new CardholderRequest("bbb", "bbb@bbb.com", "00987654321", "1234"));
        }

        assertNotNull(origem);
        assertNotNull(destination);

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