package open.api.coc.clans.clean.infrastructure.competition.persistence.mapper;

import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = CompetitionClanMapper.class
)
public interface CompetitionMapper {

    @Mapping(target = "participantClans", ignore = true)
    CompetitionEntity toEntity(Competition competition);

    @Mapping(target = "participantClans", ignore = true)
    Competition toDomain(CompetitionEntity competitionEntity);

}
