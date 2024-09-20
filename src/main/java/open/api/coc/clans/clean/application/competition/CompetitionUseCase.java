package open.api.coc.clans.clean.application.competition;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.competition.mapper.CompetitionUseCaseMapper;
import open.api.coc.clans.clean.application.competition.model.CompetitionClanScheduleCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionClanScheduleDeleteCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateClanPlayerCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateClanPlayerDeleteCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionUpdateCommand;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClanRoaster;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClanSchedule;
import open.api.coc.clans.clean.domain.competition.service.CompetitionClanScheduleService;
import open.api.coc.clans.clean.domain.competition.service.CompetitionClanRoasterService;
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
    private final CompetitionClanRoasterService competitionParticipateClanService;
    private final CompetitionClanScheduleService competitionClanScheduleService;
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

        // 2. 대회 모델 생성
        Competition competition = Competition.createNew(command.name(),
                                                        command.startDate(),
                                                        command.endDate(),
                                                        command.discordUrl(),
                                                        command.ruleBookUrl(),
                                                        command.roasterSize(),
                                                        command.restrictions(),
                                                        command.bgColor(),
                                                        command.remarks());

        // 3. 대회 저장
        Competition createdCompetition = competitionService.create(competition);

        // 4. 응답
        return competitionUseCaseMapper.toResponse(createdCompetition);
    }

    @Transactional
    public void update(CompetitionUpdateCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.id());

        // 2. 대회 모델 수정
        competition.changeCompetition(command.name(),
                                      command.startDate(),
                                      command.endDate(),
                                      command.discordUrl(),
                                      command.ruleBookUrl(),
                                      command.roasterSize(),
                                      command.restrictions(),
                                      command.bgColor(),
                                      command.remarks());

        // 3. 대회 수정
        competitionService.update(competition);
    }

    @Transactional
    public Long participate(CompetitionParticipateCreateCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.competitionId());
        // 2. 클랜 조회
        Clan clan = clanService.findById(command.clanTag());

        // 3. 대회 참여 클랜 목록 조회
        competition.loadParticipantClans(competitionParticipateService);

        // 4. 참여 클랜 모델 생성
        CompetitionClan competitionClan = CompetitionClan.createNew(competition.getId(), clan.getTag());

        // 5. 대회 참여 클랜 등록 및 검증
        competition.participateClan(competitionClan);

        // 5. 대회 저장
        CompetitionClan participateCompetitionClan = competitionParticipateService.save(competitionClan);

        // 6. 응답
        return participateCompetitionClan.getId();
    }

    @Transactional
    public void participateClanAddPlayer(CompetitionParticipateClanPlayerCreateCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.competitionId());

        // 2. 대회 참여 클랜 목록 조회
        competition.loadParticipantClans(competitionParticipateService);

        // 3. 대회 참여 클랜 조회
        CompetitionClan participantClan = competition.findParticipantClan(command.clanTag());

        // 4. 등록 멤버 조회
        participantClan.loadRoaster(competitionParticipateClanService);

        // 5. 멤버 모델 생성
        CompetitionClanRoaster clanRoaster = CompetitionClanRoaster.createNew(participantClan.getId(), command.playerTag());

        // 6. 멤버 등록 및 검증
        participantClan.addRoaster(clanRoaster);

        // 7. 멤버 저장
        competitionParticipateClanService.create(clanRoaster);
    }

    @Transactional
    public void participateClanRemovePlayer(CompetitionParticipateClanPlayerDeleteCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.competitionId());

        // 2. 대회 참여 클랜 목록 조회
        competition.loadParticipantClans(competitionParticipateService);

        // 3. 대회 참여 클랜 조회
        CompetitionClan participantClan = competition.findParticipantClan(command.clanTag());

        // 4. 멤버 삭제
        competitionParticipateClanService.remove(participantClan.getId(), command.playerTag());
    }

    @Transactional
    public void createCompetitionClanSchedule(CompetitionClanScheduleCreateCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.competitionId());

        // 2. 대회 참여 클랜 목록 조회
        competition.loadParticipantClans(competitionParticipateService);

        // 3. 대회 참여 클랜 조회
        CompetitionClan participantClan = competition.findParticipantClan(command.clanTag());

        // 4. 대회 참여 클랜 라운드 일정 조회
        participantClan.loadSchedules(competitionClanScheduleService);

        // 5. 일정 모델 생성
        CompetitionClanSchedule clanSchedule = CompetitionClanSchedule.createNew(participantClan.getId(),
                                                                                 command.description(),
                                                                                 command.startDate(),
                                                                                 command.endDate());

        // 6. 일정 등록 및 검증
        participantClan.addSchedule(clanSchedule);

        // 7. 일정 저장
        competitionClanScheduleService.create(clanSchedule);
    }

    @Transactional
    public void removeCompetitionClanSchedule(CompetitionClanScheduleDeleteCommand command) {
        // 1. 대회 조회
        Competition competition = competitionService.findById(command.competitionId());

        // 2. 대회 참여 클랜 목록 조회
        competition.loadParticipantClans(competitionParticipateService);

        // 3. 대회 참여 클랜 조회
        CompetitionClan participantClan = competition.findParticipantClan(command.clanTag());

        // 4. 대회 참여 클랜 일정 삭제
        competitionClanScheduleService.remove(command.clanScheduleId(), participantClan.getId());
    }
}
