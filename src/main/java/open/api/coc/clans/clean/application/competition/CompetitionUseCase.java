package open.api.coc.clans.clean.application.competition;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.competition.mapper.CompetitionUseCaseMapper;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.domain.competition.service.CompetitionService;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionUseCase {

    private final CompetitionService competitionService;
    private final CompetitionUseCaseMapper competitionUseCaseMapper;

    public CompetitionResponse create(CompetitionCreateCommand command) {
        // 1. 등록된 대회 검증
        competitionService.validateExists(command.name(), command.startDate(), command.endDate());

        // 2. 대회 생성
        Competition competition = Competition.createNew(command.name(),
                                                        command.startDate(),
                                                        command.endDate(),
                                                        command.discordUrl(),
                                                        command.ruleBookUrl(),
                                                        command.roasterSize(),
                                                        command.restrictions(),
                                                        command.remarks());

        Competition createdCompetition = competitionService.create(competition);

        // 3. 응답
        return competitionUseCaseMapper.toResponse(createdCompetition);
    }
}
