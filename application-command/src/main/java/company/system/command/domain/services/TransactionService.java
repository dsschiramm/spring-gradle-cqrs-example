package company.system.command.domain.services;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.models.TransactionDO;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.repositories.CardholderRepository;
import company.system.command.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardholderRepository cardholderRepository;

    public TransactionService(TransactionRepository transactionRepository, CardholderRepository cardholderRepository) {
        this.transactionRepository = transactionRepository;
        this.cardholderRepository = cardholderRepository;
    }

    public void transact(TransactionRequest transactionRequest) throws DomainException {

        UUID operationId = UUID.randomUUID();
        Instant now = Instant.now();

        Long accountOrigem = cardholderRepository.findCardholderIdByDocument(transactionRequest.documentOrigin());

        if (accountOrigem == null) {
            throw new CardholderNotFoundException();
        }

        Long accountDestination = cardholderRepository.findCardholderIdByDocument(transactionRequest.documentDestination());

        if (accountDestination == null) {
            throw new CardholderNotFoundException();
        }

        TransactionDO origemDO = new TransactionDO(operationId, now, transactionRequest.valor(), accountOrigem);
        TransactionDO destinationDO = new TransactionDO(operationId, now, transactionRequest.valor(), accountDestination);

        transactionRepository.save(List.of(origemDO, destinationDO));
    }
}
