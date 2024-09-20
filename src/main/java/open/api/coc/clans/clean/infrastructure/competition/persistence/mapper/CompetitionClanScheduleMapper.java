package open.api.coc.clans.clean.infrastructure.competition.persistence.mapper;

import open.api.coc.clans.clean.domain.competition.model.CompetitionClanSchedule;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionClanScheduleMapper {

    @Mapping(target = "compClanId", source = "competitionClan.id")
    CompetitionClanSchedule toDomain(CompetitionClanScheduleEntity entity);

    @Mapping(target = "competitionClan.id", source = "compClanId")
    CompetitionClanScheduleEntity toEntity(CompetitionClanSchedule domain);
}
