package com.jacek.documentanalysis.webapp.backend.controller.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedLogDto {
	
	List<DetailedAggregatedLogDto> detailedAggregatedLogDtoList;
}
