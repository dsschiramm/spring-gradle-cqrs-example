package company.system.command;

import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.exceptions.user.UniqueDocumentException;
import company.system.command.domain.exceptions.user.UniqueEmailException;
import company.system.command.domain.models.CardholderDO;
import company.system.command.domain.requests.CardholderRequest;
import company.system.command.domain.services.CardholderService;
import company.system.command.domain.services.TransactionService;
import company.system.command.infrastructure.repositories.CardholderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CardholderServiceTest {

    @Mock
    private CardholderRepository cardholderRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private CardholderService cardholderService;

    @Test
    @DisplayName("create success")
    void createSuccess() throws DomainException {

        when(cardholderRepository.existsByDocument(anyString())).thenReturn(false);
        when(cardholderRepository.existsByEmail(anyString())).thenReturn(false);
        when(cardholderRepository.save(any())).thenReturn(1L);

        Long id = cardholderService.create(
                new CardholderRequest("name", " name@name.com", "53610930098", "1234"));

        assertNotNull(id);

        verify(cardholderRepository).save(any(CardholderDO.class));
        verify(transactionService).credit(any(), eq(1L), eq(new BigDecimal("500.00")));
    }

    @Test
    @DisplayName("create test unique document validation")
    void uniqueDocumentTest() {

        CardholderRequest request = new CardholderRequest("name", " name@name.com", "53610930098", "1234");

        when(cardholderRepository.existsByDocument(anyString())).thenReturn(true);

        assertThrows(UniqueDocumentException.class, () -> cardholderService.create(request));
    }

    @Test
    @DisplayName("create test unique email validation")
    void uniqueEmailTest() {

        CardholderRequest request = new CardholderRequest("name", " name@name.com", "53610930098", "1234");

        when(cardholderRepository.existsByDocument(anyString())).thenReturn(false);
        when(cardholderRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(UniqueEmailException.class, () -> cardholderService.create(request));
    }

    @Test
    @DisplayName("update success")
    void updateSuccess() throws DomainException {

        CardholderRequest request = new CardholderRequest("name", " name@name.com", "53610930098", "1234");

        when(cardholderRepository.existsByDocument(anyString())).thenReturn(false);
        when(cardholderRepository.existsByEmail(request.email())).thenReturn(false);
        when(cardholderRepository.findById(1L)).thenReturn(
                new CardholderDO(UUID.randomUUID(), "bbb", "bbb@bbb.com", "79927764022", "4321", CardholderTypeEnum.MERCHANT, 1L));

        cardholderService.update(1L, request);

        ArgumentCaptor<CardholderDO> captor = ArgumentCaptor.forClass(CardholderDO.class);
        verify(cardholderRepository).update(anyLong(), captor.capture());

        CardholderDO saved = captor.getValue();

        assertNotNull(saved.getOperationId());
        assertEquals(saved.getFullName(), request.fullName());
        assertEquals(saved.getEmail(), request.email());
        assertEquals(saved.getDocument(), request.document());
        assertEquals(saved.getPassword(), request.password());
        assertEquals(CardholderTypeEnum.PERSON, saved.getType());
    }

    @Test
    @DisplayName("update error when cardholder not found")
    void updateCardholderNotFoundTest() {
        CardholderRequest request = new CardholderRequest("name", " name@name.com", "53610930098", "1234");

        when(cardholderRepository.findById(1L)).thenReturn(null);

        assertThrows(CardholderNotFoundException.class, () -> cardholderService.update(1L, request));
    }

    @Test
    @DisplayName("delete success")
    void deleteTest() throws DomainException {
        CardholderDO persisted = mock(CardholderDO.class);
        when(cardholderRepository.findById(1L)).thenReturn(persisted);

        cardholderService.delete(1L);

        verify(cardholderRepository).delete(1L);
    }

    @Test
    @DisplayName("delete error when cardholder not found")
    void deleteCardholderNotFoundTest() {
        when(cardholderRepository.findById(1L)).thenReturn(null);

        assertThrows(CardholderNotFoundException.class, () -> cardholderService.delete(1L));
    }
}