package company.system.command.services;

import company.system.command.domain.CardholderDO;
import company.system.command.exceptions.DomainException;
import company.system.command.exceptions.user.UniqueCpfException;
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

        cardholder.validate();
        validateIfCpfUnique(cardholder.getCpf());

        return cardholderRepository.save(cardholder);
    }

    public void update(Long id, CardholderDO cardholder) throws DomainException {

        cardholder.validate();

        CardholderDO persisted = cardholderRepository.findById(id);

        if (persisted == null) {
            throw new UserNotFoundException();
        }

        if (!persisted.getCpf().equals(cardholder.getCpf())) {
            validateIfCpfUnique(cardholder.getCpf());
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

    private void validateIfCpfUnique(String cpf) throws DomainException {

        if (cardholderRepository.existsByCpf(cpf)) {
            throw new UniqueCpfException();
        }
    }
}
