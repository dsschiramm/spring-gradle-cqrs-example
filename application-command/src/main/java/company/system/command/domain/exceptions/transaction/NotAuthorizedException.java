package company.system.command.domain.exceptions.transaction;

import company.system.command.domain.exceptions.DomainException;

public class NotAuthorizedException extends DomainException {

    public NotAuthorizedException() {
        super("transaction not authorized.");
    }
}
