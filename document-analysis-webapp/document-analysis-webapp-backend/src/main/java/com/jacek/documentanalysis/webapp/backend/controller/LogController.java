package com.jacek.documentanalysis.webapp.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jacek.documentanalysis.webapp.backend.controller.dto.AggregatedLogDto;
import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedLogDto;
import com.jacek.documentanalysis.webapp.backend.controller.mapper.AggregatedLogMapper;
import com.jacek.documentanalysis.webapp.backend.controller.mapper.DetailedLogMapper;
import com.jacek.documentanalysis.webapp.backend.service.LogService;

@RestController
@RequestMapping(path = "/api/log")
public class LogController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);
	
	private final LogService logService;
	
	private final AggregatedLogMapper aggregatedLogMapper;
	
	private final DetailedLogMapper detailedLogMapper;
	
	public LogController(final LogService logService,
						 final AggregatedLogMapper aggregatedLogMapper,
						 final DetailedLogMapper detailedLogMapper) {
		this.logService = logService;
		this.aggregatedLogMapper = aggregatedLogMapper;
		this.detailedLogMapper = detailedLogMapper;
	}
	
	@GetMapping
	@ResponseBody
	public AggregatedLogDto getAggregatedStatistics(@RequestParam(required = false) final String officeName,
													@RequestParam(required = false) final String userName,
													@RequestParam(required = false) final Integer dayOfMonth,
													@RequestParam(required = false) final Integer hour) {
		return this.aggregatedLogMapper.map(this.logService.readAggregatedLogs(officeName, userName, dayOfMonth, hour));
	}
	
	@GetMapping(path = "/detailed")
	@ResponseBody
	public DetailedLogDto getDetailedStatistics(@RequestParam(required = false) final String officeName,
												@RequestParam(required = false) final String userName,
												@RequestParam(required = false) final Integer dayOfMonth,
												@RequestParam(required = false) final Integer hour) {
		return this.detailedLogMapper.map(this.logService.readDetailedLogs(officeName, userName, dayOfMonth, hour));
	}
}
