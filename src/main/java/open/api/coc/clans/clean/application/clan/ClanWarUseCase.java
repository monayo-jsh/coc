package open.api.coc.clans.clean.application.clan;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.clan.service.ClanWarService;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanWarUseCase {

    private final ClanWarService clanWarService;
    private final ClanService clanService;

    private final ClanWarUseCaseMapper clanWarUseCaseMapper;

    @Transactional(readOnly = true)
    public List<ClanWarResponse> getClanWarsFromServer(ClanWarQuery query) {
        // 클랜 전쟁 목록을 조회한다.
        List<ClanWarEntity> clanWars = clanWarService.findAll(query.startDate(), query.endDate());

        // 클랜 목록을 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(clanWars.stream()
                                                                        .map(ClanWarEntity::getClanTag)
                                                                        .toList());

        // 클랜 매핑
        for(ClanWarEntity clanWar : clanWars) {
            Clan clan = clanMap.get(clanWar.getClanTag());
            clanWar.changeClan(clan);
        }

        // 응답
        return clanWars.stream()
                       .map(clanWarUseCaseMapper::toClanWarResponse)
                       .toList();
    }
}
