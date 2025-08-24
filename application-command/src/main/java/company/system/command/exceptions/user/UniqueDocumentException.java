package company.system.command.exceptions.user;

import company.system.command.exceptions.DomainException;

public class UniqueDocumentException extends DomainException {

    public UniqueDocumentException() {
        super("CPF or CNPJ already in use.");
    }
}
