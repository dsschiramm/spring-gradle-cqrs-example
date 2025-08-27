package company.system.command.domain.exceptions.transaction;

import company.system.command.domain.exceptions.DomainException;

public class MerchantTransferException extends DomainException {

    public MerchantTransferException() {
        super("the merchant cannot make this transfer operation.");
    }
}
