package com.jacek.documentanalysis.webapp.backend.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatedLogDto {
	
	private Long rowsCount;
	
	private Double avgScanTime;
	
	private Double avgSaveTime;
	
	private Double avgShowTime;
}
