package company.system.command.domain.exceptions.user;

import company.system.command.domain.exceptions.DomainException;

public class UniqueDocumentException extends DomainException {

    public UniqueDocumentException() {
        super("CPF or CNPJ already in use.");
    }
}
