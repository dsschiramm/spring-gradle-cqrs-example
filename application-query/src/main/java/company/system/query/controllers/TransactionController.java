package company.system.query.controllers;

import company.system.query.dto.TransactionDTO;
import company.system.query.repositories.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionRepository.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/cardholder/{id}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCardHolder(@PathVariable Long id) {
        List<TransactionDTO> transactions = transactionRepository.getTransactionsByCardHolder(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
