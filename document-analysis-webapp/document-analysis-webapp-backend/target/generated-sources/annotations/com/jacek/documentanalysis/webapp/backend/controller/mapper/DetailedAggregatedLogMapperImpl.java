package com.jacek.documentanalysis.webapp.backend.controller.mapper;

import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedAggregatedLogDto;
import com.jacek.documentanalysis.webapp.backend.model.DetailedAggregatedLog;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-06T21:06:40+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class DetailedAggregatedLogMapperImpl implements DetailedAggregatedLogMapper {

    @Override
    public DetailedAggregatedLogDto map(DetailedAggregatedLog detailedAggregatedLog) {
        if ( detailedAggregatedLog == null ) {
            return null;
        }

        DetailedAggregatedLogDto detailedAggregatedLogDto = new DetailedAggregatedLogDto();

        detailedAggregatedLogDto.setRowsCount( detailedAggregatedLog.getRowsCount() );
        detailedAggregatedLogDto.setOfficeName( detailedAggregatedLog.getOfficeName() );
        detailedAggregatedLogDto.setUserName( detailedAggregatedLog.getUserName() );
        detailedAggregatedLogDto.setDayOfMonth( detailedAggregatedLog.getDayOfMonth() );
        detailedAggregatedLogDto.setHour( detailedAggregatedLog.getHour() );
        detailedAggregatedLogDto.setAvgScanTime( detailedAggregatedLog.getAvgScanTime() );
        detailedAggregatedLogDto.setAvgSaveTime( detailedAggregatedLog.getAvgSaveTime() );
        detailedAggregatedLogDto.setAvgShowTime( detailedAggregatedLog.getAvgShowTime() );

        return detailedAggregatedLogDto;
    }
}
