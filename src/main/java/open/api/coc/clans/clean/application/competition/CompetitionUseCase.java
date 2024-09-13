package open.api.coc.clans.clean.application.competition;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.competition.mapper.CompetitionUseCaseMapper;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionUpdateCommand;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.domain.competition.service.CompetitionService;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionUseCase {

    private final CompetitionService competitionService;
    private final CompetitionUseCaseMapper competitionUseCaseMapper;

    @Transactional(readOnly = true)
    public List<CompetitionResponse> getCompetitions() {
        // 1. 등록된 대회 목록을 조회한다.
        List<Competition> competitions = competitionService.findAll();

        // 2. 응답
        return competitions.stream()
                           .map(competitionUseCaseMapper::toResponse)
                           .toList();
    }

    @Transactional(readOnly = true)
    public CompetitionResponse getCompetition(Long competitionId) {
        // 1. 대회 정보를 조회한다.
        Competition competition = competitionService.findById(competitionId);

        // 2. 응답
        return competitionUseCaseMapper.toResponse(competition);
    }

    @Transactional
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

    @Transactional
    public void update(CompetitionUpdateCommand command) {
        // 1. 등록된 대회 검증
        Competition competition = competitionService.findById(command.id());

        // 2. 대회 수정
        competition.changeCompetition(command.name(),
                                      command.startDate(),
                                      command.endDate(),
                                      command.discordUrl(),
                                      command.ruleBookUrl(),
                                      command.roasterSize(),
                                      command.restrictions(),
                                      command.remarks());

        competitionService.update(competition);
    }

}
