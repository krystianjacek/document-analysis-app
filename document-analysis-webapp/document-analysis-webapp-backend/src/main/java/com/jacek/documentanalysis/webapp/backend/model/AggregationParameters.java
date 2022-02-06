package com.jacek.documentanalysis.webapp.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregationParameters {
	
	private String officeName;
	
	private String userName;
	
	private Integer dayOfMonth;
	
	private Integer hour;
}
