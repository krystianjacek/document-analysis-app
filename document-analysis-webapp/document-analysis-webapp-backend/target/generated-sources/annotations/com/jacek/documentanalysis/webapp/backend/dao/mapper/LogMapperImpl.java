package com.jacek.documentanalysis.webapp.backend.dao.mapper;

import com.jacek.documentanalysis.webapp.backend.dao.entity.LogEntity;
import com.jacek.documentanalysis.webapp.backend.model.Log;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-06T21:06:40+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class LogMapperImpl implements LogMapper {

    @Override
    public LogEntity map(Log log) {
        if ( log == null ) {
            return null;
        }

        LogEntity logEntity = new LogEntity();

        logEntity.setId( log.getId() );
        logEntity.setOfficeName( log.getOfficeName() );
        logEntity.setUserName( log.getUserName() );
        logEntity.setDayOfMonth( log.getDayOfMonth() );
        logEntity.setDocumentId( log.getDocumentId() );
        logEntity.setHour( log.getHour() );
        logEntity.setScanTime( log.getScanTime() );
        logEntity.setSaveTime( log.getSaveTime() );
        logEntity.setShowTime( log.getShowTime() );

        return logEntity;
    }
}
