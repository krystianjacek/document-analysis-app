package com.jacek.documentanalysis.webapp.backend.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.util.Pair;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class LogFileEntry {
	
	@NonNull
	List<Pair<Date, String>> logLines;
	
	@NonNull
	private String officeName;
	
	@NonNull
	private String userName;
	
	@NonNull
	private Integer dayOfMonth;
	
	@NonNull
	private Integer documentId;
}
