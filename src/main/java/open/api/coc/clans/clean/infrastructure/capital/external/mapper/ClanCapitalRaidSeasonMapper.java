package open.api.coc.clans.clean.infrastructure.capital.external.mapper;

import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.infrastructure.capital.external.dto.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ClanCapitalRaidSeasonMapper {

    @Autowired
    private TimeConverter timeConverter;

    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    public abstract ClanCapitalRaidSeason toDomain(ClanCapitalRaidSeasonResponse source);

    protected LocalDateTime map(String time) {
        long epochMilli = timeConverter.toEpochMilliSecond(time);
        return timeConverter.toLocalDateTime(epochMilli);
    }

}
