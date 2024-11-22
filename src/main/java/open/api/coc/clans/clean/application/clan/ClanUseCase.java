package open.api.coc.clans.clean.application.clan;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.dto.ClanContentUpdateCommand;
import open.api.coc.clans.clean.application.clan.dto.ClanQueryCommand;
import open.api.coc.clans.clean.application.clan.mapper.ClanUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanMember;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.domain.clan.service.ClanQueryService;
import open.api.coc.clans.clean.domain.clan.service.ClanRegistrationService;
import open.api.coc.clans.clean.presentation.clan.dto.ClanDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.ClanMemberResponse;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanUseCase {

    private final ClanClient clanClient;
    private final ClanRepository clanRepository;

    private final ClanRegistrationService clanRegistrationService;
    private final ClanQueryService clanQueryService;

    private final ClanUseCaseMapper clanUseCaseMapper;

    @Transactional(readOnly = true)
    public List<ClanResponse> getActiveClans(ClanQueryCommand command) {
        if (command.isSearchType()) {
            // 검색 조건 존재 시
            List<Clan> clans = clanQueryService.findAllActiveContentByClanContentType(command.type());
            return mapToClanResponse(clans);
        }

        // 활성화된 전체 클랜 목록을 조회한다.
        List<Clan> clans = clanRepository.findAllActiveClans();
        return mapToClanResponse(clans);
    }

    private List<ClanResponse> mapToClanResponse(List<Clan> clans) {
        return clans.stream()
                    .map(clanUseCaseMapper::toClanResponse)
                    .toList();
    }

    @Transactional
    public ClanDetailResponse getClanDetailFromExternal(String clanTag) {
        // COC API 연동
        Clan latestClan = clanClient.findByTag(clanTag);

        // 서버에 저장된 클랜이 있다면 클랜 정보 현행화
        clanRegistrationService.synchronizeIfExists(latestClan);

        // 응답 반환
        return clanUseCaseMapper.toClanDetailResponse(latestClan);
    }

    @Transactional
    public List<ClanMemberResponse> getClanMembersFromExternal(String clanTag) {
        // COC API 연동
        List<ClanMember> members = clanClient.findMembersByTag(clanTag);

        // 응답 반환
        return members.stream()
                      .map(clanUseCaseMapper::toClanMemberResponse)
                      .toList();
    }

    @Transactional
    public ClanResponse registerClan(String clanTag) {
        // COC API 요청으로 클랜의 최신 정보를 조회한다.
        Clan latestClan = clanClient.findByTag(clanTag);

        // 클랜을 생성하거나 활성화 설정
        Clan clan = clanRegistrationService.createOrActivate(latestClan);

        // 클랜 저장
        clanRegistrationService.save(clan);

        // 응답
        return clanUseCaseMapper.toClanResponse(clan);
    }

    @Transactional
    public void deleteClan(String clanTag) {
        // 클랜 조회
        Clan clan = clanRepository.findById(clanTag)
                                  .orElseThrow(() -> new ClanNotExistsException(clanTag));

        // 클랜 비활성화
        clan.deactivate();

        // 클랜 저장
        clanRegistrationService.save(clan);
    }

    @Transactional
    public void updateContentActivation(ClanContentUpdateCommand command) {
        // 클랜 조회
        Clan clan = clanRepository.findById(command.clanTag())
                                  .orElseThrow(() -> new ClanNotExistsException(command.clanTag()));

        // 클랜 컨텐츠 활성화 업데이트
        clan.changeContentActivation(command.clanWarYn(),
                                     command.clanWarLeagueYn(),
                                     command.clanWarParallelYn(),
                                     command.clanCapitalYn());

        // 클랜 저장
        clanRegistrationService.save(clan);
    }

}
