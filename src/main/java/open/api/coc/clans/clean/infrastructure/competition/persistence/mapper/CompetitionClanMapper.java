package open.api.coc.clans.clean.infrastructure.competition.persistence.mapper;

import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionClanMapper {

    @Mapping(target = "competition.id", source = "compId")
    CompetitionClanEntity toEntity(CompetitionClan competitionClan);

    @Mapping(target = "compId", source = "competition.id")
    CompetitionClan toDomain(CompetitionClanEntity competitionClanEntity);

}
