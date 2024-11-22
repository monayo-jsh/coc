package open.api.coc.clans.clean.application.clan;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMemberLeagueRecordQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMemberQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMemberRecordQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMissingAttackPlayerQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMissingAttackQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantMissingAttackDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantRecordDTO;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMemberRecordSearchCriteria;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMissingAttackSearchCriteria;
import open.api.coc.clans.clean.domain.clan.service.ClanWarParticipantService;
import open.api.coc.clans.clean.domain.clan.service.ClanWarRecordService;
import open.api.coc.clans.clean.domain.clan.service.ClanWarService;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberMissingAttackResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberRecordResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarParticipantResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanWarUseCase {

    private final ClanWarService clanWarService;
    private final ClanWarParticipantService participantService;
    private final ClanWarRecordService recordService;

    private final ClanWarUseCaseMapper clanWarUseCaseMapper;

    @Transactional(readOnly = true)
    public List<ClanWarResponse> getClanWarsFromServer(ClanWarQuery query) {
        // 클랜 전쟁 목록을 조회한다.
        List<ClanWarDTO> clanWars = clanWarService.findAllDTO(query.startDate(), query.endDate());

        // 응답
        return clanWars.stream()
                       .map(clanWarUseCaseMapper::toClanWarResponse)
                       .toList();
    }

    @Transactional(readOnly = true)
    public ClanWarDetailResponse getClanWarDetail(Long warId) {
        // 클랜 전쟁 정보를 조회한다.
        ClanWarDTO clanWar = clanWarService.findDTOWithAllByIdOrThrow(warId);

        // 응답
        return clanWarUseCaseMapper.toClanWarDetailResponse(clanWar);
    }

    @Transactional(readOnly = true)
    public List<ClanWarParticipantResponse> getClanWarParticipants(ClanWarMemberQuery query) {
        // 클랜 전쟁 정보를 조회한다.
        ClanWarDTO clanWar = clanWarService.findDTOWithAllByClanTagAndStartTimeOrThrow(query.clanTag(), query.startTime());

        // 필수 참석 여부 조건에 따른 참여자 목록을 조회한다.
        List<ClanWarParticipantDTO> members = clanWar.getNecessaryAttackParticipants(query.necessaryAttackYn());

        return members.stream()
                      .map(clanWarUseCaseMapper::toClanWarParticipantResponse)
                      .toList();
    }

    @Transactional
    public void changeClanWarMemberAttackNecessaryAttack(Long warId, String playerTag) {
        // 클랜 전쟁 정보를 조회한다.
        ClanWarEntity clanWar = clanWarService.findByIdOrThrow(warId);

        // 클랜 참여자의 필수 참석 여부를 전환한다.
        clanWar.changeMemberNecessaryAttack(playerTag);

        // 클랜 참여자 정보를 저장한다.
        clanWarService.save(clanWar);
    }

    @Transactional(readOnly = true)
    public List<ClanWarMemberMissingAttackResponse> getClanWarMissingAttackPlayers(ClanWarMissingAttackQuery query) {
        // 조회를 위한 크리테리아 획득
        ClanWarMissingAttackSearchCriteria criteria = query.toSearchCriteria();

        // 미공 기록 조회
        List<ClanWarParticipantMissingAttackDTO> missingAttacks = participantService.getMissingAttacks(criteria);

        // 응
        return missingAttacks.stream()
                             .map(clanWarUseCaseMapper::toClanWarMemberMissingAttackResponse)
                             .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanWarMemberMissingAttackResponse> getClanWarMissingAttacks(ClanWarMissingAttackPlayerQuery query) {
        // 조회를 위한 크리테리아 획득
        ClanWarMissingAttackSearchCriteria criteria = query.toSearchCriteria();

        // 미공 기록 조회
        List<ClanWarParticipantMissingAttackDTO> missingAttacks = participantService.getMissingAttacks(criteria);

        // 응답
        return missingAttacks.stream()
                             .map(clanWarUseCaseMapper::toClanWarMemberMissingAttackResponse)
                             .sorted(Comparator.comparing(ClanWarMemberMissingAttackResponse::getTag)
                                               .thenComparing(ClanWarMemberMissingAttackResponse::getStartTime).reversed())
                             .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanWarMemberRecordResponse> getClanWarMemberRecords(ClanWarMemberRecordQuery query) {
        // 조회를 위한 크리테리아 획득
        ClanWarMemberRecordSearchCriteria criteria = query.toSearchCriteria();

        // 전쟁 기록을 조회한다.
        List<ClanWarParticipantRecordDTO> records = recordService.getMemberRecords(criteria);

        // 응답
        return records.stream()
                      .map(clanWarUseCaseMapper::toClanWarMemberRecordResponse)
                      .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanWarMemberRecordResponse> getLeagueWarMemberRecords(ClanWarMemberLeagueRecordQuery query) {
        // 조회를 위한 크리테리아 획득
        ClanWarMemberRecordSearchCriteria criteria = query.toSearchCriteria();

        // 전쟁 기록을 조회한다.
        List<ClanWarParticipantRecordDTO> records = recordService.getMemberRecords(criteria);

        if (query.searchAll()) {
            return records.stream()
                          .map(clanWarUseCaseMapper::toClanWarMemberRecordResponse)
                          .toList();
        }

        // 클랜별 리그전 라운드 수를 조회한다.
        Map<String, Integer> leagueWarRoundMapByClan = clanWarService.findLeagueWarRoundCountMap(criteria.from(), criteria.to());

        // 응답
        return records.stream()
                      .filter(record -> record.isAllRoundDestroy(leagueWarRoundMapByClan))
                      .map(clanWarUseCaseMapper::toClanWarMemberRecordResponse)
                      .toList();
    }
}
