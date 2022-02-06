package com.jacek.documentanalysis.webapp.backend.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jacek.documentanalysis.webapp.backend.model.EventLog;

@Component
public class RabbitMQSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);
	
	private final RabbitTemplate template;
	
	@Value("${eventlog.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${eventlog.rabbitmq.routingkey}")
	private String routingkey;
	
	public RabbitMQSender(final RabbitTemplate template) {
		this.template = template;
	}
	
	public void send(final EventLog eventLog) {
		this.template.convertAndSend(this.exchange, this.routingkey, eventLog);
		
		LOGGER.info("Send message: {}", eventLog);
	}
}
