package br.com.emergency.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaxCapacityNotificationPublisher {
    private final RabbitTemplate rabbitTemplate;

    public MaxCapacityNotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(MaxCapacityNotificationEvent event) {
        rabbitTemplate.convertAndSend("max-capacity-exchange", "max.capacity", event);
    }
}
