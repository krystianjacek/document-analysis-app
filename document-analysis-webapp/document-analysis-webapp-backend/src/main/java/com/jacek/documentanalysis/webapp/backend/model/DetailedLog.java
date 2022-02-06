package com.jacek.documentanalysis.webapp.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedLog {
	List<DetailedAggregatedLog> detailedAggregatedLogList;
}
