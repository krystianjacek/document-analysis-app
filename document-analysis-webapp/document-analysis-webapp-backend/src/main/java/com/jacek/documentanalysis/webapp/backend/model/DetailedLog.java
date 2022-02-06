package com.jacek.documentanalysis.webapp.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailedLog {
	List<DetailedAggregatedLog> detailedAggregatedLogList;
}
