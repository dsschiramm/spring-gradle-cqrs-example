package company.system.command.exceptions.user;

import company.system.command.exceptions.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException() {
        super("user not found.");
    }
}
