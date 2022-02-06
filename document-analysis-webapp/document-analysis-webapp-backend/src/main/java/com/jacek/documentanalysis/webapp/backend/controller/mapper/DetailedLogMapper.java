package com.jacek.documentanalysis.webapp.backend.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedLogDto;
import com.jacek.documentanalysis.webapp.backend.model.DetailedLog;

@Mapper(componentModel = "spring", uses = DetailedAggregatedLogMapper.class)
public interface DetailedLogMapper {
	
	@Mappings({
			@Mapping(target = "detailedAggregatedLogDtoList", source = "detailedAggregatedLogList")
	})
	DetailedLogDto map(final DetailedLog detailedLog);
}
