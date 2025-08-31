package company.system.command.application;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.requests.TransactionRequest;
import company.system.command.domain.services.TransactionService;
import company.system.utils.models.output.GenericResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<GenericResponseDTO> transact(
            @RequestBody @Valid TransactionRequest transactionRequest
    ) throws DomainException {

        UUID operationId = transactionService.transact(transactionRequest);

        return new ResponseEntity<>(
                new GenericResponseDTO(operationId, "transaction confirmed."),
                HttpStatus.CREATED);
    }
}
