package company.system.command.exceptions.user;

import company.system.command.exceptions.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException() {
        super("NOT_FOUND", "user not found.", 404);
    }
}
