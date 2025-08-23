package company.system.command.controllers;

import company.system.command.domain.CardholderDO;
import company.system.command.exceptions.DomainException;
import company.system.command.services.CardholderService;
import company.system.utils.models.output.GenericResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class CardholderController {

    private final CardholderService cardholderService;

    public CardholderController(CardholderService cardholderService) {
        this.cardholderService = cardholderService;
    }

    @PostMapping
    public ResponseEntity<GenericResponseDTO> create(
            @RequestBody CardholderDO cardholderDO
    ) throws DomainException {

        Long createdId = cardholderService.create(cardholderDO);

        return new ResponseEntity<>(
                new GenericResponseDTO(createdId, "Cardholder created."),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CardholderDO cardholderDO
    ) throws DomainException {

        cardholderService.update(id, cardholderDO);

        return new ResponseEntity<>(
                new GenericResponseDTO(null, "Cardholder updated."),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> delete(@PathVariable Long id) throws DomainException {

        cardholderService.delete(id);

        return new ResponseEntity<>(
                new GenericResponseDTO(null, "Cardholder deleted."),
                HttpStatus.OK);
    }
}
