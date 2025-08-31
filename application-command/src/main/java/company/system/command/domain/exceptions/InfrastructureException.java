package company.system.command.domain.exceptions;

public class InfrastructureException extends Exception {

    private final String code;
    private final Integer status;

    public InfrastructureException(String message) {
        super(message);
        this.code = "INFRASTRUCTURE_ERROR";
        this.status = 500;
    }

    public InfrastructureException(String message, Integer status) {
        super(message);
        this.code = "INFRASTRUCTURE_ERROR";
        this.status = status;
    }
}