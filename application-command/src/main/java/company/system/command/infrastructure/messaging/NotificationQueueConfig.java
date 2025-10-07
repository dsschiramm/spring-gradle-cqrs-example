package company.system.command.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationQueueConfig {

    @Value("${messaging.exchanges.direct}")
    private String MAIN_EXCHANGE;

    @Value("${messaging.exchanges.dlx}")
    private String DLX_EXCHANGE;

    @Value("${messaging.queues.transaction-received}")
    private String QUEUE;

    @Value("${messaging.routing-key.transaction-received}")
    private String ROUTING_KEY;

    @Value("${messaging.queues.transaction-received-dlq}")
    private String DLQ_QUEUE;

    @Bean
    public DirectExchange mainExchange() {
        return ExchangeBuilder.directExchange(MAIN_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue transactionReceivedQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding bindTransactionQueue(Queue transactionReceivedQueue, DirectExchange mainExchange) {
        return BindingBuilder.bind(transactionReceivedQueue).to(mainExchange).with(ROUTING_KEY);
    }

    @Bean
    public DirectExchange dlxExchange() {
        return ExchangeBuilder.directExchange(DLX_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public Binding bindDLQ(Queue dlqQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(dlqQueue).to(dlxExchange).with(ROUTING_KEY);
    }
}
