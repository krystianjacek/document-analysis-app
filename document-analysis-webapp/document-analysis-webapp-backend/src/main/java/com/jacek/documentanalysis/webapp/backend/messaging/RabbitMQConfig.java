package com.jacek.documentanalysis.webapp.backend.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Value("${eventlog.rabbitmq.queue}")
	String queueName;
	
	@Value("${eventlog.rabbitmq.exchange}")
	String exchange;
	
	@Value("${eventlog.rabbitmq.routingkey}")
	private String routingkey;
	
	@Bean
	Queue queue() {
		return new Queue(this.queueName, false);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(this.exchange);
	}
	
	@Bean
	Binding binding(final Queue queue, final DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(this.routingkey);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
