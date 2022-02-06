package com.jacek.documentanalysis.webapp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.jacek.documentanalysis.webapp.backend.spring.DocumentAnalysisSpringConfig;

@SpringBootApplication
@EnableAsync
@Import(DocumentAnalysisSpringConfig.class)
public class DocumentAnalysis {
	
	public static void main(final String[] args) {
		SpringApplication.run(DocumentAnalysis.class, args);
	}
}
