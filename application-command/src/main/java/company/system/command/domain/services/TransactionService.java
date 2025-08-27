package company.system.command.domain.services;

import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.transaction.MerchantTransferException;
import company.system.command.domain.exceptions.transaction.OutOfFundsException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.models.CardholderDO;
import company.system.command.domain.models.TransactionDO;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.repositories.CardholderRepository;
import company.system.command.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public void credit(UUID operationId, Long cardholderId, BigDecimal valor) {
        transactionRepository.save(new TransactionDO(operationId, valor, cardholderId));
    }

    public UUID transact(TransactionRequest transactionRequest) throws DomainException {

        UUID operationId = UUID.randomUUID();
        Instant now = Instant.now();

        CardholderDO accountOrigem = cardholderRepository.findCardholderByDocument(transactionRequest.documentOrigin());

        if (accountOrigem == null) {
            throw new CardholderNotFoundException();
        }

        if (CardholderTypeEnum.MERCHANT.equals(accountOrigem.getType())) {
            throw new MerchantTransferException();
        }

        outOfFundsValidation(accountOrigem.getId(), transactionRequest.valor());

        CardholderDO accountDestination = cardholderRepository.findCardholderByDocument(transactionRequest.documentDestination());

        if (accountDestination == null) {
            throw new CardholderNotFoundException();
        }

        TransactionDO origem = new TransactionDO(operationId, now, transactionRequest.valor().negate(), accountOrigem.getId());
        TransactionDO destination = new TransactionDO(operationId, now, transactionRequest.valor(), accountDestination.getId());

        transactionRepository.save(List.of(origem, destination));

        return operationId;
    }

    private void outOfFundsValidation(Long cardholderId, BigDecimal valor) throws DomainException {

        List<TransactionDO> transactions = transactionRepository.findAllByCardholder(cardholderId);

        BigDecimal funds = transactions.stream()
                .map(TransactionDO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        funds = funds.subtract(valor);

        if (funds.compareTo(BigDecimal.ZERO) < 0) {
            throw new OutOfFundsException();
        }
    }
}
