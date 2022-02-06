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
import org.springframework.stereotype.Repository;

import com.jacek.documentanalysis.webapp.backend.common.Defaults;
import com.jacek.documentanalysis.webapp.backend.dao.SearchParams;
import com.jacek.documentanalysis.webapp.backend.dao.entity.LogEntity;
import com.jacek.documentanalysis.webapp.backend.dao.repository.ExtendedLogRepository;
import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.AggregationParameters;

@Repository
public class ExtendedLogRepositoryImpl implements ExtendedLogRepository {
	
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
		final AggregatedLog aggregatedLog = new AggregatedLog();
		final Object[] result = (Object[]) object;
		aggregatedLog.setRowsCount((long) result[0]);
		aggregatedLog.setAvgScanTime((double) result[1]);
		aggregatedLog.setAvgSaveTime((double) result[2]);
		aggregatedLog.setAvgShowTime((double) result[3]);
		return aggregatedLog;
	}
	
	private List<AggregationParameters> extractAggregationParameters(final List<Object> resultList) {
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
			final Path<String> dayOfMonthPath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_DAY_OF_MONTH);
			wherePredicates.add(criteriaBuilder.equal(dayOfMonthPath, searchParams.getDayOfMonth()));
		}
		if (searchParams.getHour() > 0) {
			final Path<String> hourPath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_HOUR);
			wherePredicates.add(criteriaBuilder.equal(hourPath, searchParams.getHour()));
		}
		if (!StringUtils.isEmpty(searchParams.getOfficeName())) {
			final Path<String> officeNamePath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_OFFICE_NAME);
			wherePredicates.add(criteriaBuilder.equal(officeNamePath, searchParams.getOfficeName()));
		}
		if (!StringUtils.isEmpty(searchParams.getUserName())) {
			final Path<String> userNamePath = logStatisticEntityRoot.get(Defaults.LogEntity.FIELD_USER_NAME);
			wherePredicates.add(criteriaBuilder.equal(userNamePath, searchParams.getUserName()));
		}
		return wherePredicates;
	}
}
