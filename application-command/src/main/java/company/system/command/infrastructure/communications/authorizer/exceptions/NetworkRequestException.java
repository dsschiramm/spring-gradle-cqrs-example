package company.system.command.infrastructure.communications.authorizer.exceptions;

import company.system.command.domain.exceptions.InfrastructureException;

public class NetworkRequestException extends InfrastructureException {

    public NetworkRequestException(String message) {
        super("Network error in request: " + message);
    }
}