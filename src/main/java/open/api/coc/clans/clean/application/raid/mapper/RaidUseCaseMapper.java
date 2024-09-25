package open.api.coc.clans.clean.application.raid.mapper;

import java.time.LocalDate;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class RaidUseCaseMapper {

    @Autowired
    private TimeConverter timeConverter;

    @Mapping(target = "startTime", source = "startDate")
    @Mapping(target = "endTime", source = "endDate")
    public abstract ClanCapitalRaidResponse toResponse(ClanCapitalRaid source);

    protected Long map(LocalDate localDateTime) {
        return timeConverter.toEpochMilliSecond(localDateTime);
    }
}
