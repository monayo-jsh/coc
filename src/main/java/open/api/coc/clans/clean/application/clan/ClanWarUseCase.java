package open.api.coc.clans.clean.application.clan;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMemberQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMissingAttackPlayerQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMissingAttackQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMissingAttackSearchCriteria;
import open.api.coc.clans.clean.domain.clan.service.ClanWarMemberService;
import open.api.coc.clans.clean.domain.clan.service.ClanWarService;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberMissingAttackResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanWarUseCase {

    private final ClanWarService clanWarService;
    private final ClanWarMemberService clanWarMemberService;

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
    public List<ClanWarMemberResponse> getClanWarMembers(ClanWarMemberQuery query) {
        // 클랜 전쟁 정보를 조회한다.
        ClanWarDTO clanWar = clanWarService.findDTOWithAllByClanTagAndStartTimeOrThrow(query.clanTag(), query.startTime());

        // 필수 참석 여부 조건에 따른 참여자 목록을 조회한다.
        List<ClanWarMemberDTO> members = clanWar.getNecessaryAttackMembers(query.necessaryAttackYn());

        return members.stream()
                      .map(clanWarUseCaseMapper::toClanWarMemberResponse)
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
        // 조회를 위한 조회 크리테리아 획득
        ClanWarMissingAttackSearchCriteria criteria = query.toSearchCriteria();

        // 미공 기록 조회
        List<ClanWarMemberMissingAttackDTO> missingAttacks = clanWarMemberService.findMissingAttacks(criteria);

        // 응
        return missingAttacks.stream()
                             .map(clanWarUseCaseMapper::toClanWarMemberMissingAttackResponse)
                             .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanWarMemberMissingAttackResponse> getClanWarMissingAttacks(ClanWarMissingAttackPlayerQuery query) {
        // 조회를 위한 조회 크리테리아 획득
        ClanWarMissingAttackSearchCriteria criteria = query.toSearchCriteria();

        // 미공 기록 조회
        List<ClanWarMemberMissingAttackDTO> missingAttacks = clanWarMemberService.findMissingAttacks(criteria);

        // 응답
        return missingAttacks.stream()
                             .map(clanWarUseCaseMapper::toClanWarMemberMissingAttackResponse)
                             .sorted(Comparator.comparing(ClanWarMemberMissingAttackResponse::getTag)
                                               .thenComparing(ClanWarMemberMissingAttackResponse::getStartTime).reversed())
                             .toList();
    }

}
