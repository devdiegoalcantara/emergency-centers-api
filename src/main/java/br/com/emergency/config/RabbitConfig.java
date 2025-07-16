package br.com.emergency.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public TopicExchange maxCapacityExchange() {
        return new TopicExchange("max-capacity-exchange");
    }

    @Bean
    public Queue maxCapacityQueue() {
        return new Queue("max-capacity-queue");
    }

    @Bean
    public Binding binding(Queue maxCapacityQueue, TopicExchange maxCapacityExchange) {
        return BindingBuilder.bind(maxCapacityQueue).to(maxCapacityExchange).with("max.capacity");
    }
}
