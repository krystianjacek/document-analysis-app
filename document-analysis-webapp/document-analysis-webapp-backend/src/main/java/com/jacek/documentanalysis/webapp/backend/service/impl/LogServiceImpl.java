package com.jacek.documentanalysis.webapp.backend.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jacek.documentanalysis.webapp.backend.dao.SearchParams;
import com.jacek.documentanalysis.webapp.backend.dao.mapper.LogMapper;
import com.jacek.documentanalysis.webapp.backend.dao.repository.LogRepository;
import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.AggregationParameters;
import com.jacek.documentanalysis.webapp.backend.model.DetailedAggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.DetailedLog;
import com.jacek.documentanalysis.webapp.backend.model.Log;
import com.jacek.documentanalysis.webapp.backend.model.LogFileEntry;
import com.jacek.documentanalysis.webapp.backend.service.LogService;

@Service
public class LogServiceImpl implements LogService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
	
	private final LogRepository logRepository;
	
	private final LogMapper logMapper;
	
	public LogServiceImpl(final LogRepository logRepository, final LogMapper logMapper) {
		this.logRepository = logRepository;
		this.logMapper = logMapper;
	}
	
	private static long getDateDiff(final Date date1, final Date date2) {
		final long diff = Math.abs(date2.getTime() - date1.getTime());
		return TimeUnit.MILLISECONDS.toMillis(diff);
	}
	
	@Override
	@Transactional
	public void processLog(final LogFileEntry logFileEntry) {
		LOGGER.debug("Process log file entry : {}", logFileEntry);
		final Log log = this.constructLog(logFileEntry);
		
		LOGGER.debug("Save log {}", log);
		this.logRepository.save(this.logMapper.map(log));
	}
	
	@Override
	@Transactional(readOnly = true)
	public AggregatedLog readAggregatedLogs(final String officeName, final String userName, final Integer dayOfMonth, final Integer hour) {
		LOGGER.debug("Read aggregated logs for office {}, user {}, day {} and hour {}", officeName, userName, dayOfMonth, hour);
		return this.logRepository.findAggregatedLogByCriteria(SearchParams.SearchParamsBuilder
				.newInstance()
				.dayOfMonth(dayOfMonth)
				.officeName(officeName)
				.userName(userName)
				.hour(hour)
				.build());
	}
	
	@Override
	@Transactional(readOnly = true)
	public DetailedLog readDetailedLogs(final String officeName, final String userName, final Integer dayOfMonth, final Integer hour) {
		LOGGER.debug("Read detailed logs for office {}, user {}, day {} and hour {}", officeName, userName, dayOfMonth, hour);
		final SearchParams searchParams = SearchParams.SearchParamsBuilder
				.newInstance()
				.dayOfMonth(dayOfMonth)
				.officeName(officeName)
				.userName(userName)
				.hour(hour)
				.build();
		
		final List<AggregationParameters> aggregationParametersList = this.logRepository.findAggregationParameters(searchParams);
		LOGGER.debug("Found {} aggregation parameters", aggregationParametersList.size());
		
		final List<DetailedAggregatedLog> detailedAggregatedLogList = new ArrayList<>();
		for (final AggregationParameters aggregationParameters : aggregationParametersList) {
			final SearchParams aggregationSearchParams = SearchParams.SearchParamsBuilder
					.newInstance()
					.dayOfMonth(aggregationParameters.getDayOfMonth())
					.officeName(aggregationParameters.getOfficeName())
					.userName(aggregationParameters.getUserName())
					.hour(aggregationParameters.getHour())
					.build();
			
			LOGGER.debug("Find aggregated log for following aggregation search params : {}", aggregationSearchParams);
			final AggregatedLog aggregatedLogByCriteria = this.logRepository.findAggregatedLogByCriteria(aggregationSearchParams);
			
			//TODO: this object construction could be improved...
			final DetailedAggregatedLog detailedAggregatedLog = new DetailedAggregatedLog();
			detailedAggregatedLog.setRowsCount(aggregatedLogByCriteria.getRowsCount());
			detailedAggregatedLog.setAvgScanTime(aggregatedLogByCriteria.getAvgScanTime());
			detailedAggregatedLog.setAvgSaveTime(aggregatedLogByCriteria.getAvgSaveTime());
			detailedAggregatedLog.setAvgShowTime(aggregatedLogByCriteria.getAvgShowTime());
			detailedAggregatedLog.setOfficeName(aggregationParameters.getOfficeName());
			detailedAggregatedLog.setUserName(aggregationParameters.getUserName());
			detailedAggregatedLog.setDayOfMonth(aggregationParameters.getDayOfMonth());
			detailedAggregatedLog.setHour(aggregationParameters.getHour());
			
			detailedAggregatedLogList.add(detailedAggregatedLog);
			LOGGER.debug("Found detailed aggregated log : {}", detailedAggregatedLog);
		}
		
		final DetailedLog detailedLog = new DetailedLog();
		detailedLog.setDetailedAggregatedLogList(detailedAggregatedLogList);
		return detailedLog;
	}
	
	private Log constructLog(final LogFileEntry logFileEntry) {
		final Log log = new Log();
		log.setDayOfMonth(logFileEntry.getDayOfMonth());
		log.setDocumentId(logFileEntry.getDocumentId());
		log.setOfficeName(logFileEntry.getOfficeName());
		log.setUserName(logFileEntry.getUserName());
		log.setHour(this.calculateHour(logFileEntry));
		log.setScanTime(this.calculateScanTime(logFileEntry));
		log.setSaveTime(this.calculateSaveTime(logFileEntry));
		log.setShowTime(this.calculateShowTime(logFileEntry));
		return log;
	}
	
	private long calculateScanTime(final LogFileEntry logFileEntry) {
		return getDateDiff(logFileEntry.getLogLines().get(0).getFirst(),
				logFileEntry.getLogLines().get(1).getFirst());
	}
	
	private long calculateSaveTime(final LogFileEntry logFileEntry) {
		return getDateDiff(logFileEntry.getLogLines().get(2).getFirst(),
				logFileEntry.getLogLines().get(3).getFirst());
	}
	
	private long calculateShowTime(final LogFileEntry logFileEntry) {
		return getDateDiff(logFileEntry.getLogLines().get(4).getFirst(),
				logFileEntry.getLogLines().get(5).getFirst());
	}
	
	private Integer calculateHour(final LogFileEntry logFileEntry) {
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(logFileEntry.getLogLines().get(5).getFirst());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
}
