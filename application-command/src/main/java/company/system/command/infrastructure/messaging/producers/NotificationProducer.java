package company.system.command.infrastructure.messaging.producers;

import company.system.command.domain.ports.dtos.NotificationDTO;
import company.system.command.domain.ports.services.INotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer implements INotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${messaging.queues.transaction-received}")
    private String queueTransactionReceived;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(NotificationDTO notification) {
        rabbitTemplate.convertAndSend(queueTransactionReceived, notification);
    }
}
