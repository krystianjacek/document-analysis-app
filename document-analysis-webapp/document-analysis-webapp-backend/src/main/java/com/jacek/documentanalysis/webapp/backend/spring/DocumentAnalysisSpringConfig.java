package com.jacek.documentanalysis.webapp.backend.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
		"com.jacek.documentanalysis.webapp.backend.api",
		"com.jacek.documentanalysis.webapp.backend.controller",
		"com.jacek.documentanalysis.webapp.backend.dao",
		"com.jacek.documentanalysis.webapp.backend.eventlogreader",
		"com.jacek.documentanalysis.webapp.backend.logfilewatcher",
		"com.jacek.documentanalysis.webapp.backend.messaging",
		"com.jacek.documentanalysis.webapp.backend.service"
})
public class DocumentAnalysisSpringConfig {
}
