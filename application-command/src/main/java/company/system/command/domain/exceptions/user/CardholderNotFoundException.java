package company.system.command.domain.exceptions.user;

import company.system.command.domain.exceptions.DomainException;

public class CardholderNotFoundException extends DomainException {

    public CardholderNotFoundException() {
        super("NOT_FOUND", "user not found.", 404);
    }
}
