package company.system.command.exceptions.user;

import company.system.command.exceptions.DomainException;

public class UniqueCpfException extends DomainException {

    public UniqueCpfException() {
        super("CPF already in use.");
    }
}
