package com.jacek.documentanalysis.webapp.backend.eventlogreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.jacek.documentanalysis.webapp.backend.model.EventLog;
import com.jacek.documentanalysis.webapp.backend.model.LogFileEntry;

@Component
public class EventLogReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventLogReader.class);
	
	private final static Pattern FILE_NAME_PATTERN = Pattern.compile("([a-zA-Z\\d]+)_([a-zA-Z\\d]+)_(\\d)+_DocumentApp_([a-zA-Z\\d]+)\\.log");
	
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
	
	public LogFileEntry parse(final EventLog eventLog) {
		LOGGER.info("Start parsing event log : {}", eventLog);
		
		final Matcher matcher = FILE_NAME_PATTERN.matcher(eventLog.getFileName());
		if (matcher.matches()) {
			final LogFileEntry logFileEntry = new LogFileEntry();
			logFileEntry.setOfficeName(matcher.group(1));
			logFileEntry.setUserName(matcher.group(2));
			logFileEntry.setDayOfMonth(Integer.parseInt(matcher.group(3)));
			logFileEntry.setDocumentId(Integer.parseInt(matcher.group(4)));
			final List<Pair<Date, String>> logLines = new ArrayList<>();
			
			final Path path = Paths.get(eventLog.getPath(), eventLog.getFileName());
			
			try (final BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
				String currentLine;
				while ((currentLine = reader.readLine()) != null) {
					final List<String> logLine = Arrays.asList(currentLine.split(" ", 2));
					final Date date = this.convertTimeToMilliseconds(logLine.get(0));
					logLines.add(Pair.of(date, logLine.get(1)));
				}
			}
			catch (final IOException | ParseException e) {
				e.printStackTrace();
			}
			logFileEntry.setLogLines(logLines);
			LOGGER.info("Log entries : {}", logFileEntry);
			return logFileEntry;
		}
		else {
			return new LogFileEntry();
			//handle properly
//			throw new RuntimeException();
		}
	}
	
	private Date convertTimeToMilliseconds(final String time) throws ParseException {
		return TIME_FORMAT.parse(time);
	}
}
