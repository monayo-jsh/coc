package open.api.coc.clans.clean.application.clan;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.dto.ClanContentUpdateCommand;
import open.api.coc.clans.clean.application.clan.mapper.ClanUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanContentType;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanUseCase {

    private final ClanClient clanClient;
    private final ClanRepository clanRepository;

    private final ClanService clanService;

    private final ClanUseCaseMapper clanUseCaseMapper;

    @Transactional(readOnly = true)
    public List<ClanResponse> getClans() {
        // 활성화된 전체 클랜 목록을 조회한다.
        List<Clan> clans = clanRepository.findAllActiveClans();

        // 응답 반환
        return convertToClanResponse(clans);
    }

    @Transactional
    public ClanResponse registerClan(String clanTag) {
        // COC API 요청으로 클랜의 최신 정보를 조회한다.
        Clan latestClan = clanClient.findByTag(clanTag);

        // 클랜을 생성하거나 활성화 설정
        Clan clan = clanService.createOrActivate(latestClan);

        // 클랜 저장
        clanService.save(clan);

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
        clanService.save(clan);
    }

    @Transactional
    public void updateContentActivation(ClanContentUpdateCommand command) {
        Clan clan = clanService.findById(command.clanTag());

        clan.changeContentActivation(command.clanWarYn(),
                                     command.clanWarLeagueYn(),
                                     command.clanWarParallelYn(),
                                     command.clanCapitalYn());

        clanService.save(clan);
    }

    public List<ClanResponse> getWarClans(String warType) {
        // 클랜 목록을 조회한다.
        List<Clan> clans = clanService.findAllByWarType(warType);

        // 응답
        return convertToClanResponse(clans);
    }

    public List<ClanResponse> getCompetitionClans() {
        // 클랜 목록을 조회한다.
        List<Clan> clans = clanService.findAllByClanContentType(ClanContentType.CLAN_COMPETITION);

        // 응답
        return convertToClanResponse(clans);
    }

    private List<ClanResponse> convertToClanResponse(List<Clan> clans) {
        return clans.stream()
                    .map(clanUseCaseMapper::toClanResponse)
                    .toList();
    }

}
