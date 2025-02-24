package company.system.command.exceptions.user;

import company.system.command.exceptions.DomainException;

public class InvalidCpfException extends DomainException {

    public InvalidCpfException() {
        super("invalid CPF.");
    }
}
