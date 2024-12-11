package open.api.coc.clans.clean.application.competition.mapper;

import open.api.coc.clans.clean.domain.competition.model.CompetitionClanSchedule;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionClanScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionClanScheduleUseCaseMapper {

    CompetitionClanScheduleResponse toResponse(CompetitionClanSchedule competitionClanSchedule);

}
