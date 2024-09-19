package open.api.coc.clans.clean.application.competition;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.competition.mapper.CompetitionUseCaseMapper;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionUpdateCommand;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.domain.competition.service.CompetitionParticipateService;
import open.api.coc.clans.clean.domain.competition.service.CompetitionService;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionDetailResponse;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionUseCase {

    private final ClanService clanService;

    private final CompetitionService competitionService;
    private final CompetitionParticipateService competitionParticipateService;
    private final CompetitionUseCaseMapper competitionUseCaseMapper;

    @Transactional(readOnly = true)
    public List<CompetitionDetailResponse> getCompetitions() {
        // 1. 등록된 대회 목록을 조회한다.
        List<Competition> competitions = competitionService.findAll();

        // 2. 대회 참여 클랜을 조회한다.
        competitions.forEach(competition -> competition.loadParticipantClans(competitionParticipateService));

        // 3. 응답
        return competitions.stream()
                           .map(competitionUseCaseMapper::toDetailResponse)
                           .toList();
    }

    @Transactional(readOnly = true)
    public CompetitionResponse getCompetition(Long competitionId) {
        // 1. 대회 정보를 조회한다.
        Competition competition = competitionService.findById(competitionId);

        // 2. 대회 참여 클랜을 조회한다.
        competition.loadParticipantClans(competitionParticipateService);

        // 3. 응답
        return competitionUseCaseMapper.toDetailResponse(competition);
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
                                                        command.bgColor(),
                                                        command.remarks());

        Competition createdCompetition = competitionService.create(competition);

        // 3. 응답
        return competitionUseCaseMapper.toResponse(createdCompetition);
    }

    @Transactional
    public void update(CompetitionUpdateCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.id());

        // 2. 대회 수정
        competition.changeCompetition(command.name(),
                                      command.startDate(),
                                      command.endDate(),
                                      command.discordUrl(),
                                      command.ruleBookUrl(),
                                      command.roasterSize(),
                                      command.restrictions(),
                                      command.bgColor(),
                                      command.remarks());

        competitionService.update(competition);
    }

    @Transactional
    public Long participate(CompetitionParticipateCreateCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.competitionId());
        // 2. 클랜 조회
        Clan clan = clanService.findById(command.clanTag());

        // 3. 대회 참여 상태 검증
        competition.validateAlreadyParticipated(clan);

        // 4. 대회 참가
        CompetitionClan competitionClan = CompetitionClan.createNew(competition.getId(), command.clanTag());
        CompetitionClan participateCompetitionClan = competitionParticipateService.save(competitionClan);

        // 5. 응답
        return participateCompetitionClan.getId();
    }

}
