package company.system.command.domain.ports.services;

import company.system.command.domain.ports.dtos.NotificationDTO;

public interface INotificationService {

    void send(NotificationDTO notification);
}
