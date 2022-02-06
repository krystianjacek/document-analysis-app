package com.jacek.documentanalysis.webapp.backend.eventlogreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.jacek.documentanalysis.webapp.backend.model.EventLog;
import com.jacek.documentanalysis.webapp.backend.model.LogFileEntry;
import com.jacek.documentanalysis.webapp.backend.service.LogService;

@Component
@RabbitListener(queues = "${eventlog.rabbitmq.queue}")
public class EventLogConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventLogConsumer.class);
	
	private final EventLogReader eventLogReader;
	
	private final LogService logService;
	
	public EventLogConsumer(final EventLogReader eventLogReader,
							final LogService logService) {
		this.eventLogReader = eventLogReader;
		this.logService = logService;
	}
	
	@RabbitHandler
	public void receive(final EventLog eventLog) {
		LOGGER.info(" --> Received {}", eventLog);
		final LogFileEntry logFileEntry = this.eventLogReader.parse(eventLog);
		this.logService.processLog(logFileEntry);
	}
}
