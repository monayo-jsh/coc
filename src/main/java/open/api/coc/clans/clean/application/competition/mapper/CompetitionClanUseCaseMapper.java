package open.api.coc.clans.clean.application.competition.mapper;

import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionClanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionClanUseCaseMapper {

    CompetitionClanResponse toResponse(CompetitionClan competitionClan);

}
