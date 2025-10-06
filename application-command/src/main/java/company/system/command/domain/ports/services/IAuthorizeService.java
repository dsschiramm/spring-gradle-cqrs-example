package company.system.command.domain.ports.services;

import company.system.command.domain.exceptions.InfrastructureException;

public interface IAuthorizeService {

    Boolean authorize() throws InfrastructureException;
}
