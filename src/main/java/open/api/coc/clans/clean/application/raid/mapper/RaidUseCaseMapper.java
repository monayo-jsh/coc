package open.api.coc.clans.clean.application.raid.mapper;

import java.time.LocalDate;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidScoreResponse;
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

    @Mapping(target = "id", source = "member.id")
    @Mapping(target = "tag", source = "member.tag")
    @Mapping(target = "name", source = "member.name")
    @Mapping(target = "attacks", source = "member.attacks")
    @Mapping(target = "resourceLooted", source = "member.capitalResourcesLooted")
    @Mapping(target = "seasonStartDate", source = "raid.startDate")
    @Mapping(target = "seasonEndDate", source = "raid.endDate")
    @Mapping(target = "clan", source = "clan")
    public abstract ClanCapitalRaidScoreResponse toResponse(ClanCapitalRaidMember member, ClanCapitalRaid raid, Clan clan);

    protected Long map(LocalDate localDate) {
        return timeConverter.toEpochMilliSecond(localDate);
    }
}
