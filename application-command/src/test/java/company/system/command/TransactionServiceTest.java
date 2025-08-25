package company.system.command;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.transaction.OutOfFundsException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("Deve lançar CardholderNotFoundException quando o portador do cartão de origem não for encontrado")
    void transact_shouldThrowException_whenOriginCardholderNotFound() throws DomainException {

        String originDocument = "12345678900";
        String destinationDocument = "00987654321";
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
    @DisplayName("Deve lançar CardholderNotFoundException quando o portador do cartão de destino não for encontrado")
    void transact_shouldThrowException_whenDestinationCardholderNotFound() {

        String originDocument = "12345678900";
        String destinationDocument = "00987654321";
        BigDecimal amount = new BigDecimal("75.00");

        TransactionRequest request = new TransactionRequest(originDocument, destinationDocument, amount);

        when(cardholderRepository.findCardholderIdByDocument(originDocument)).thenReturn(null);
        when(cardholderRepository.findCardholderIdByDocument(destinationDocument)).thenReturn(null);

        assertThrows(CardholderNotFoundException.class, () -> transactionService.transact(request));
    }
}