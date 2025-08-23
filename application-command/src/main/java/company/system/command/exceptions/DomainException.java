package company.system.command.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends Exception {

    private final String code;

    public DomainException(String message) {
        super(message);
        this.code = "DOMAIN_ERROR";
    }

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
    }
}
