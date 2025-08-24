package company.system.command.services;

import company.system.command.domain.CardholderDO;
import company.system.command.exceptions.DomainException;
import company.system.command.exceptions.user.UniqueDocumentException;
import company.system.command.exceptions.user.UniqueEmailException;
import company.system.command.exceptions.user.UserNotFoundException;
import company.system.command.repositories.CardholderRepository;
import org.springframework.stereotype.Service;

@Service
public class CardholderService {

    private final CardholderRepository cardholderRepository;

    public CardholderService(CardholderRepository cardholderRepository) {
        this.cardholderRepository = cardholderRepository;
    }

    public Long create(CardholderDO cardholder) throws DomainException {

        validateIfDocumentUnique(cardholder.getDocument());
        validateIfEmailUnique(cardholder.getEmail());

        return cardholderRepository.save(cardholder);
    }

    public void update(Long id, CardholderDO cardholder) throws DomainException {

        CardholderDO persisted = cardholderRepository.findById(id);

        if (persisted == null) {
            throw new UserNotFoundException();
        }

        if (!persisted.getDocument().equals(cardholder.getDocument())) {
            validateIfDocumentUnique(cardholder.getDocument());
        }

        if (!persisted.getEmail().equals(cardholder.getEmail())) {
            validateIfEmailUnique(cardholder.getDocument());
        }

        cardholderRepository.update(id, cardholder);
    }

    public void delete(Long id) throws DomainException {

        CardholderDO persisted = cardholderRepository.findById(id);

        if (persisted == null) {
            throw new UserNotFoundException();
        }

        cardholderRepository.delete(id);
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
