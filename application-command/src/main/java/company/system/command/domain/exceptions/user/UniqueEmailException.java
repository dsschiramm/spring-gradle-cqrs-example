package company.system.command.domain.exceptions.user;

import company.system.command.domain.exceptions.DomainException;

public class UniqueEmailException extends DomainException {

    public UniqueEmailException() {
        super("Email already in use.");
    }
}
