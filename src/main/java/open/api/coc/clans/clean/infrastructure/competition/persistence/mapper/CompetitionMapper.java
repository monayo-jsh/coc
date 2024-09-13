package open.api.coc.clans.clean.infrastructure.competition.persistence.mapper;

import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionMapper {

    CompetitionEntity toEntity(Competition competition);

    Competition toDomain(CompetitionEntity competitionEntity);

}
