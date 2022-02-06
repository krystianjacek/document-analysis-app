package com.jacek.documentanalysis.webapp.backend.controller.mapper;

import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedAggregatedLogDto;
import com.jacek.documentanalysis.webapp.backend.controller.dto.DetailedLogDto;
import com.jacek.documentanalysis.webapp.backend.model.DetailedAggregatedLog;
import com.jacek.documentanalysis.webapp.backend.model.DetailedLog;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-06T21:06:40+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class DetailedLogMapperImpl implements DetailedLogMapper {

    @Autowired
    private DetailedAggregatedLogMapper detailedAggregatedLogMapper;

    @Override
    public DetailedLogDto map(DetailedLog detailedLog) {
        if ( detailedLog == null ) {
            return null;
        }

        DetailedLogDto detailedLogDto = new DetailedLogDto();

        detailedLogDto.setDetailedAggregatedLogDtoList( detailedAggregatedLogListToDetailedAggregatedLogDtoList( detailedLog.getDetailedAggregatedLogList() ) );

        return detailedLogDto;
    }

    protected List<DetailedAggregatedLogDto> detailedAggregatedLogListToDetailedAggregatedLogDtoList(List<DetailedAggregatedLog> list) {
        if ( list == null ) {
            return null;
        }

        List<DetailedAggregatedLogDto> list1 = new ArrayList<DetailedAggregatedLogDto>( list.size() );
        for ( DetailedAggregatedLog detailedAggregatedLog : list ) {
            list1.add( detailedAggregatedLogMapper.map( detailedAggregatedLog ) );
        }

        return list1;
    }
}
