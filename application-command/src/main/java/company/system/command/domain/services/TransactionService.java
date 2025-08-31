package company.system.command.domain.services;

import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.exceptions.InfrastructureException;
import company.system.command.domain.exceptions.transaction.AuthorizeServiceException;
import company.system.command.domain.exceptions.transaction.MerchantTransferException;
import company.system.command.domain.exceptions.transaction.NotAuthorizedException;
import company.system.command.domain.exceptions.transaction.OutOfFundsException;
import company.system.command.domain.exceptions.user.CardholderNotFoundException;
import company.system.command.domain.models.CardholderDO;
import company.system.command.domain.models.TransactionDO;
import company.system.command.domain.ports.IAuthorizeService;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.infrastructure.repositories.CardholderRepository;
import company.system.command.infrastructure.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardholderRepository cardholderRepository;
    private final IAuthorizeService authorizeService;

    public TransactionService(
            TransactionRepository transactionRepository,
            CardholderRepository cardholderRepository,
            IAuthorizeService authorizeService
    ) {
        this.transactionRepository = transactionRepository;
        this.cardholderRepository = cardholderRepository;
        this.authorizeService = authorizeService;
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

        try {
            Boolean authorized = authorizeService.authorize();

            if (!Boolean.TRUE.equals(authorized)) {
                throw new NotAuthorizedException();
            }
        } catch (InfrastructureException e) {
            throw new AuthorizeServiceException();
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
