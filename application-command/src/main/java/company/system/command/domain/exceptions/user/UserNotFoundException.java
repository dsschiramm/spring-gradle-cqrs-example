package company.system.command.domain.exceptions.user;

import company.system.command.domain.exceptions.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException() {
        super("NOT_FOUND", "user not found.", 404);
    }
}
