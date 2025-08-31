package company.system.command.infrastructure.communications.authorizer.exceptions;

import company.system.command.domain.exceptions.InfrastructureException;

public class ResponseRequestException extends InfrastructureException {

    public ResponseRequestException(String message, int code) {
        super("Response error in request (" + code + "): " + message, code);
    }
}