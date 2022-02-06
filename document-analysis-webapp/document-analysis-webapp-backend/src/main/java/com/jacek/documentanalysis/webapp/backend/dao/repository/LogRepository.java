package com.jacek.documentanalysis.webapp.backend.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacek.documentanalysis.webapp.backend.dao.entity.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Long>, ExtendedLogRepository {

}
