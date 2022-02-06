package com.jacek.documentanalysis.webapp.backend.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedAggregatedLogDto {
	
	private Long rowsCount;
	
	private String officeName;
	
	private String userName;
	
	private Integer dayOfMonth;
	
	private Integer hour;
	
	private Double avgScanTime;
	
	private Double avgSaveTime;
	
	private Double avgShowTime;
}
