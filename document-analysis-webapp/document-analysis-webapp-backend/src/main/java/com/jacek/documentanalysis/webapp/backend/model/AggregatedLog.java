package com.jacek.documentanalysis.webapp.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatedLog {
	
	private Long rowsCount;
	
	private Double avgScanTime;
	
	private Double avgSaveTime;
	
	private Double avgShowTime;
}
