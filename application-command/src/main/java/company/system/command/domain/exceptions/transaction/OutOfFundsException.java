package company.system.command.domain.exceptions.transaction;

import company.system.command.domain.exceptions.DomainException;

public class OutOfFundsException extends DomainException {

    public OutOfFundsException() {
        super("out of funds.");
    }
}
