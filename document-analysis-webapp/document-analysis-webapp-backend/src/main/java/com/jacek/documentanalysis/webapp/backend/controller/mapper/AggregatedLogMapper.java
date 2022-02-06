package com.jacek.documentanalysis.webapp.backend.controller.mapper;

import org.mapstruct.Mapper;

import com.jacek.documentanalysis.webapp.backend.controller.dto.AggregatedLogDto;
import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;

@Mapper(componentModel = "spring")
public interface AggregatedLogMapper {
	
	AggregatedLogDto map(final AggregatedLog aggregatedLog);
}
