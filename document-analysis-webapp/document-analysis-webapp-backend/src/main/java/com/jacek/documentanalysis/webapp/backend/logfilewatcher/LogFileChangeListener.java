package com.jacek.documentanalysis.webapp.backend.logfilewatcher;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import com.jacek.documentanalysis.webapp.backend.messaging.RabbitMQSender;
import com.jacek.documentanalysis.webapp.backend.model.EventLog;

@Component
public class LogFileChangeListener implements FileChangeListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogFileChangeListener.class);
	
	private final RabbitMQSender rabbitMQSender;
	
	public LogFileChangeListener(final RabbitMQSender rabbitMQSender) {
		this.rabbitMQSender = rabbitMQSender;
	}
	
	@Override
	public void onChange(final Set<ChangedFiles> changeSet) {
		for (final ChangedFiles changedFiles : changeSet) {
			for (final ChangedFile changedFile : changedFiles.getFiles()) {
				if (changedFile.getType() == ChangedFile.Type.ADD
						&& !this.isLocked(changedFile.getFile().toPath())) {
					LOGGER.info("Operation: {} on file {} ", changedFile.getType(), changedFile.getFile().getName());
					
					this.rabbitMQSender.send(EventLog.builder().fileName(changedFile.getFile().getName()).path(changedFiles.getSourceDirectory().getPath()).build());
				}
			}
		}
	}
	
	private boolean isLocked(final Path path) {
		try (final FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); final FileLock lock = ch.tryLock()) {
			return lock == null;
		}
		catch (final IOException e) {
			//handle properly
			return true;
		}
	}
	
}
