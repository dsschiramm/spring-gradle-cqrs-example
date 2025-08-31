package company.system.command.domain.exceptions.transaction;

import company.system.command.domain.exceptions.DomainException;

public class AuthorizeServiceException extends DomainException {

    public AuthorizeServiceException() {
        super("external transaction authorization service failed, please try again later.");
    }
}
