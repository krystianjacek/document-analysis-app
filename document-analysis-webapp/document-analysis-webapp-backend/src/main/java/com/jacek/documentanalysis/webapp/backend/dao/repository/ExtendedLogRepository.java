package com.jacek.documentanalysis.webapp.backend.dao.repository;

import java.util.List;

import com.jacek.documentanalysis.webapp.backend.dao.SearchParams;
import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.AggregationParameters;

public interface ExtendedLogRepository {
	
	AggregatedLog findAggregatedLogByCriteria(final SearchParams searchParams);
	
	List<AggregationParameters> findAggregationParameters(final SearchParams searchParams);
}
