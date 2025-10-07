package company.system.command.domain.services;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.exceptions.user.UniqueDocumentException;
import company.system.command.domain.exceptions.user.UniqueEmailException;
import company.system.command.domain.models.CardholderDO;
import company.system.command.domain.requests.CardholderRequest;
import company.system.command.infrastructure.repositories.CardholderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardholderService {

    private final CardholderRepository cardholderRepository;
    private final TransactionService transactionService;

    public CardholderService(CardholderRepository cardholderRepository, TransactionService transactionService) {
        this.cardholderRepository = cardholderRepository;
        this.transactionService = transactionService;
    }

    public Long create(CardholderRequest cardholderRequest) throws DomainException {

        CardholderDO cardholder = new CardholderDO(cardholderRequest);

        validateIfDocumentUnique(cardholder.getDocument());
        validateIfEmailUnique(cardholder.getEmail());

        Long cardholderId = cardholderRepository.save(cardholder);

        transactionService.credit(cardholder.getOperationId(), cardholderId, new BigDecimal("500.00"));

        return cardholderId;
    }

    public void update(Long id, CardholderRequest cardholderRequest) throws DomainException {

        CardholderDO cardholder = new CardholderDO(cardholderRequest);
        CardholderDO persisted = cardholderRepository.findById(id);

        if (persisted == null) {
            throw new CardholderNotFoundException();
        }

        if (!persisted.getDocument().equals(cardholder.getDocument())) {
            validateIfDocumentUnique(cardholder.getDocument());
        }

        if (!persisted.getEmail().equals(cardholder.getEmail())) {
            validateIfEmailUnique(cardholder.getEmail());
        }

        cardholderRepository.update(id, cardholder);
    }

    public void delete(Long id) throws DomainException {

        CardholderDO persisted = cardholderRepository.findById(id);

        if (persisted == null) {
            throw new CardholderNotFoundException();
        }

        if (transactionService.cardholderHasTransaction(id)) {
            persisted.desativar();
            cardholderRepository.update(id, persisted);
        } else {
            cardholderRepository.delete(id);
        }
    }

    private void validateIfDocumentUnique(String document) throws DomainException {

        if (cardholderRepository.existsByDocument(document)) {
            throw new UniqueDocumentException();
        }
    }

    private void validateIfEmailUnique(String email) throws DomainException {

        if (cardholderRepository.existsByEmail(email)) {
            throw new UniqueEmailException();
        }
    }
}
