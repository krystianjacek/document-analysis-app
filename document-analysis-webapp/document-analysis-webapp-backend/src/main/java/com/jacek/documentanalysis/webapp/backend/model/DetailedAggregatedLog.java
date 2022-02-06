package com.jacek.documentanalysis.webapp.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailedAggregatedLog {
	
	private Long rowsCount;
	
	private String officeName;
	
	private String userName;
	
	private Integer dayOfMonth;
	
	private Integer hour;
	
	private Double avgScanTime;
	
	private Double avgSaveTime;
	
	private Double avgShowTime;
	
	
}
