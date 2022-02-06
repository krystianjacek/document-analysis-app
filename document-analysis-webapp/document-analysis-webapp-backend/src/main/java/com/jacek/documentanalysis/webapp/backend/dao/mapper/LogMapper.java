package com.jacek.documentanalysis.webapp.backend.dao.mapper;

import org.mapstruct.Mapper;

import com.jacek.documentanalysis.webapp.backend.dao.entity.LogEntity;
import com.jacek.documentanalysis.webapp.backend.model.Log;

@Mapper(componentModel = "spring")
public interface LogMapper {
	
	LogEntity map(final Log log);
}
