package com.jacek.documentanalysis.webapp.backend.dao.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jacek.documentanalysis.webapp.backend.common.Defaults;
import com.jacek.documentanalysis.webapp.backend.dao.SearchParams;
import com.jacek.documentanalysis.webapp.backend.dao.entity.LogEntity;
import com.jacek.documentanalysis.webapp.backend.dao.repository.ExtendedLogRepository;
import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.AggregationParameters;

@Repository
public class ExtendedLogRepositoryImpl implements ExtendedLogRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedLogRepositoryImpl.class);
	
	private final EntityManager entityManager;
	
	public ExtendedLogRepositoryImpl(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public AggregatedLog findAggregatedLogByCriteria(final SearchParams searchParams) {
		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);
		final Root<LogEntity> logStatisticEntityRoot = criteriaQuery.from(LogEntity.class);
		
		final List<Predicate> wherePredicates = this.getWherePredicates(searchParams, criteriaBuilder, logStatisticEntityRoot);
		
		final Expression<Double> avgScanTime = criteriaBuilder.avg(logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_SCAN_TIME));
		final Expression<Double> avgSaveTime = criteriaBuilder.avg(logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_SAVE_TIME));
		final Expression<Double> avgShowTime = criteriaBuilder.avg(logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_SHOW_TIME));
		
		criteriaQuery.multiselect(
				criteriaBuilder.count(logStatisticEntityRoot),
				avgScanTime,
				avgSaveTime,
				avgShowTime);
		criteriaQuery.where(wherePredicates.toArray(new Predicate[0]));
		
		final Object result = this.entityManager.createQuery(criteriaQuery).getSingleResult();
		return this.extractAggregatedLog(result);
	}

	
	@Override
	public List<AggregationParameters> findAggregationParameters(final SearchParams searchParams) {
		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);
		final Root<LogEntity> logStatisticEntityRoot = criteriaQuery.from(LogEntity.class);
		
		final List<Predicate> wherePredicates = this.getWherePredicates(searchParams, criteriaBuilder, logStatisticEntityRoot);
		
		criteriaQuery.multiselect(
				logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_OFFICE_NAME),
				logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_USER_NAME),
				logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_DAY_OF_MONTH),
				logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_HOUR)).distinct(true);
		criteriaQuery.where(wherePredicates.toArray(new Predicate[0]));
		
		final List<Object> result = this.entityManager.createQuery(criteriaQuery).getResultList();
		return this.extractAggregationParameters(result);
	}
	
	private AggregatedLog extractAggregatedLog(final Object object) {
		LOGGER.debug("Extract object to aggregated log : {}", object);
		
		final AggregatedLog aggregatedLog = new AggregatedLog();
		final Object[] result = (Object[]) object;
		aggregatedLog.setRowsCount(result[0] == null ? null : (Long) result[0]);
		aggregatedLog.setAvgScanTime(result[1] == null ? null : (Double) result[1]);
		aggregatedLog.setAvgSaveTime(result[2] == null ? null : (Double) result[2]);
		aggregatedLog.setAvgShowTime(result[3] == null ? null : (Double) result[3]);
		return aggregatedLog;
	}
	
	private List<AggregationParameters> extractAggregationParameters(final List<Object> resultList) {
		LOGGER.debug("Extract objects list to list of aggregation parameters : {}", resultList);
		
		final List<AggregationParameters> aggregationParametersList = new ArrayList<>();
		for(final Object o : resultList) {
			final Object[] result = (Object[]) o;
			final AggregationParameters aggregationParameters = new AggregationParameters();
			aggregationParameters.setOfficeName((String)result[0]);
			aggregationParameters.setUserName((String)result[1]);
			aggregationParameters.setDayOfMonth((Integer)result[2]);
			aggregationParameters.setHour((Integer)result[3]);
			aggregationParametersList.add(aggregationParameters);
		}
		return aggregationParametersList;
	}
	
	private List<Predicate> getWherePredicates(final SearchParams searchParams, final CriteriaBuilder criteriaBuilder, final Root<LogEntity> logStatisticEntityRoot) {
		final List<Predicate> wherePredicates = new ArrayList<>();
		if (searchParams.getDayOfMonth() > 0) {
			LOGGER.debug("day of month is defined: {}", searchParams.getDayOfMonth());
			final Path<String> dayOfMonthPath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_DAY_OF_MONTH);
			wherePredicates.add(criteriaBuilder.equal(dayOfMonthPath, searchParams.getDayOfMonth()));
		}
		if (searchParams.getHour() > 0) {
			LOGGER.debug("hour is defined: {}", searchParams.getHour());
			final Path<String> hourPath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_HOUR);
			wherePredicates.add(criteriaBuilder.equal(hourPath, searchParams.getHour()));
		}
		if (!StringUtils.isEmpty(searchParams.getOfficeName())) {
			LOGGER.debug("office name is defined: {}", searchParams.getOfficeName());
			final Path<String> officeNamePath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_OFFICE_NAME);
			wherePredicates.add(criteriaBuilder.equal(officeNamePath, searchParams.getOfficeName()));
		}
		if (!StringUtils.isEmpty(searchParams.getUserName())) {
			LOGGER.debug("user name is defined: {}", searchParams.getUserName());
			final Path<String> userNamePath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_USER_NAME);
			wherePredicates.add(criteriaBuilder.equal(userNamePath, searchParams.getUserName()));
		}
		return wherePredicates;
	}
}
