package com.jacek.documentanalysis.webapp.backend.dao;

import lombok.Getter;

@Getter
public class SearchParams {
	private String officeName;
	
	private String userName;
	
	private Integer dayOfMonth;
	
	private Integer hour;
	
	public static class SearchParamsBuilder {
		
		private String officeName;
		private String userName;
		private Integer dayOfMonth;
		private Integer hour;
		
		public static SearchParamsBuilder newInstance() {
			return new SearchParamsBuilder();
		}
		
		public SearchParamsBuilder officeName(final String officeName) {
			this.officeName = officeName == null ? "" : officeName;
			return this;
		}
		
		public SearchParamsBuilder userName(final String userName) {
			this.userName = userName == null ? "" : userName;
			return this;
		}
		
		public SearchParamsBuilder dayOfMonth(final Integer dayOfMonth) {
			this.dayOfMonth = dayOfMonth == null ? -1 : dayOfMonth;
			return this;
		}
		
		public SearchParamsBuilder hour(final Integer hour) {
			this.hour = hour == null ? -1 : hour;
			return this;
		}
		
		public SearchParams build() {
			final SearchParams searchParams = new SearchParams();
			searchParams.officeName = this.officeName;
			searchParams.userName = this.userName;
			searchParams.dayOfMonth = this.dayOfMonth;
			searchParams.hour = this.hour;
			return searchParams;
		}
	}
}
