package com.jacek.documentanalysis.webapp.backend.controller.mapper;

import com.jacek.documentanalysis.webapp.backend.controller.dto.AggregatedLogDto;
import com.jacek.documentanalysis.webapp.backend.model.AggregatedLog;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-06T21:06:40+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class AggregatedLogMapperImpl implements AggregatedLogMapper {

    @Override
    public AggregatedLogDto map(AggregatedLog aggregatedLog) {
        if ( aggregatedLog == null ) {
            return null;
        }

        AggregatedLogDto aggregatedLogDto = new AggregatedLogDto();

        aggregatedLogDto.setRowsCount( aggregatedLog.getRowsCount() );
        aggregatedLogDto.setAvgScanTime( aggregatedLog.getAvgScanTime() );
        aggregatedLogDto.setAvgSaveTime( aggregatedLog.getAvgSaveTime() );
        aggregatedLogDto.setAvgShowTime( aggregatedLog.getAvgShowTime() );

        return aggregatedLogDto;
    }
}
