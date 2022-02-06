package com.jacek.documentanalysis.webapp.backend.service;

import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.DetailedLog;
import com.jacek.documentanalysis.webapp.backend.model.LogFileEntry;

public interface LogService {
	
	void processLog(final LogFileEntry logFileEntry);
	
	AggregatedLog readAggregatedLogs(final String officeName, final String userName, final Integer dayOfMonth, final Integer hour);
	
	DetailedLog readDetailedLogs(final String officeName, final String userName, final Integer dayOfMonth, final Integer hour);
}
