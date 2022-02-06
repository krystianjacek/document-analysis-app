package com.jacek.documentanalysis.webapp.backend.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedAggregatedLogDto;
import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedLogDto;
import com.jacek.documentanalysis.webapp.backend.model.DetailedAggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.DetailedLog;

@Mapper(componentModel = "spring")
public interface DetailedAggregatedLogMapper {

	DetailedAggregatedLogDto map(final DetailedAggregatedLog detailedAggregatedLog);
}
