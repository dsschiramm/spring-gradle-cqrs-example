package company.system.command.exceptions.user;

import company.system.command.exceptions.DomainException;

public class UniqueEmailException extends DomainException {

    public UniqueEmailException() {
        super("Email already in use.");
    }
}
