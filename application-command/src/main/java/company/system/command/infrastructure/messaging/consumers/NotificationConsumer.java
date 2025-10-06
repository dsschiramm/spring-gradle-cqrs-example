package company.system.command.infrastructure.messaging.consumers;

import company.system.command.domain.exceptions.InfrastructureException;
import company.system.command.domain.ports.dtos.NotificationDTO;
import company.system.command.infrastructure.communications.notification.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${messaging.queues.transaction-received}")
    public void listen(NotificationDTO notification) throws InfrastructureException {
        notificationService.send(notification);
    }
}
