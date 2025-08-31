package company.system.command.infrastructure.communications.authorizer.exceptions;

import company.system.command.domain.exceptions.InfrastructureException;

public class NullableHttpResponseException extends InfrastructureException {

    public NullableHttpResponseException() {
        super("Void response from server");
    }
}
