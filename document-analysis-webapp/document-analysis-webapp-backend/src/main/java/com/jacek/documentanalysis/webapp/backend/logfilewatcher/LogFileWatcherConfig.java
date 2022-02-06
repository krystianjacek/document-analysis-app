package com.jacek.documentanalysis.webapp.backend.logfilewatcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFileWatcherConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogFileWatcherConfig.class);
	
	
	private final LogFileChangeListener logFileChangeListener;
	
	public LogFileWatcherConfig(final LogFileChangeListener logFileChangeListener) {
		this.logFileChangeListener = logFileChangeListener;
	}
	
	@Bean
	public FileSystemWatcher logFileWatcher() {
		final FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
		final Path pathToWatchLogs = Paths.get(System.getProperty("user.home"), "logs");
		fileSystemWatcher.addSourceDirectory(pathToWatchLogs.toFile());
		fileSystemWatcher.addListener(this.logFileChangeListener);
		fileSystemWatcher.start();
		
		LOGGER.info("Started LogFileWatcher in directory : {}", pathToWatchLogs);
		
		return fileSystemWatcher;
	}
	
	@PreDestroy
	public void onDestroy() {
		this.logFileWatcher().stop();
	}
}
