package company.system.query.controllers;

import company.system.query.dto.CardholderDTO;
import company.system.query.repositories.CardholderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cardholders")
public class CardholderController {

    private final CardholderRepository cardholderRepository;

    public CardholderController(CardholderRepository cardholderRepository) {
        this.cardholderRepository = cardholderRepository;
    }

    @GetMapping
    public ResponseEntity<List<CardholderDTO>> getAllUsers() {
        List<CardholderDTO> users = cardholderRepository.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardholderDTO> getUserById(@PathVariable Long id) {
        CardholderDTO user = cardholderRepository.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
