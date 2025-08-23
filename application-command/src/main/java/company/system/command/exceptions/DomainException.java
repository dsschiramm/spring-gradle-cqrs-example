package company.system.command.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends Exception {

    private final String code;
    private final Integer status;

    public DomainException(String message) {
        super(message);
        this.code = "DOMAIN_ERROR";
        this.status = 422;
    }

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
        this.status = 422;
    }

    public DomainException(String code, String message, Integer status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
