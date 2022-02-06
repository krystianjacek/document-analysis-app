package com.jacek.documentanalysis.webapp.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Log {
	
	private Long id;
	
	private String officeName;
	
	private String userName;
	
	private Integer dayOfMonth;
	
	private Integer hour;
	
	private Integer documentId;
	
	private Long scanTime;
	
	private Long saveTime;
	
	private Long showTime;
}
