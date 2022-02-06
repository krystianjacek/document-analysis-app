package com.jacek.documentanalysis.webapp.backend.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jacek.documentanalysis.webapp.backend.common.Defaults;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Defaults.LogEntity.TABLE_NAME)
public class LogEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Defaults.LogEntity.COLUMN_ID)
	private Long id;
	
	@Column(name = Defaults.LogEntity.COLUMN_OFFICE_NAME)
	private String officeName;
	
	@Column(name = Defaults.LogEntity.COLUMN_USER_NAME)
	private String userName;
	
	@Column(name = Defaults.LogEntity.COLUMN_DAY_OF_MONTH)
	private Integer dayOfMonth;
	
	@Column(name = Defaults.LogEntity.COLUMN_DOCUMENT_ID)
	private Integer documentId;
	
	@Column(name = Defaults.LogEntity.COLUMN_HOUR)
	private Integer hour;
	
	@Column(name = Defaults.LogEntity.COLUMN_SCAN_TIME)
	private Long scanTime;
	
	@Column(name = Defaults.LogEntity.COLUMN_SAVE_TIME)
	private Long saveTime;
	
	@Column(name = Defaults.LogEntity.COLUMN_SHOW_TIME)
	private Long showTime;
	
}
