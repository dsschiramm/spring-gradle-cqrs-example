package company.system.command.controllers;

import company.system.command.domain.exceptions.DomainException;
import company.system.command.domain.requests.CardholderRequest;
import company.system.command.domain.services.CardholderService;
import company.system.utils.models.output.GenericResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cardholders")
public class CardholderController {

    private final CardholderService cardholderService;

    public CardholderController(CardholderService cardholderService) {
        this.cardholderService = cardholderService;
    }

    @PostMapping
    public ResponseEntity<GenericResponseDTO> create(
            @RequestBody @Valid CardholderRequest cardholderRequest
    ) throws DomainException {

        Long createdId = cardholderService.create(cardholderRequest);

        return new ResponseEntity<>(
                new GenericResponseDTO(createdId, "Cardholder created."),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CardholderRequest cardholderRequest
    ) throws DomainException {

        cardholderService.update(id, cardholderRequest);

        return new ResponseEntity<>(
                new GenericResponseDTO("Cardholder updated."),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> delete(@PathVariable Long id) throws DomainException {

        cardholderService.delete(id);

        return new ResponseEntity<>(
                new GenericResponseDTO("Cardholder deleted."),
                HttpStatus.OK);
    }
}
