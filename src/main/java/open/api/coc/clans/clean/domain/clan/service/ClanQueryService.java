package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanContentType;
import open.api.coc.clans.clean.domain.clan.repository.ClanLeagueWarRepository;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanQueryService {

    private final ClanRepository clanRepository;
    private final ClanLeagueWarRepository leagueWarRepository;

    public List<Clan> findAllActiveContentByClanContentType(ClanContentType clanContentType) {
        // 컨텐츠 활성화 클랜 목록을 조회한다.
        List<Clan> clans = clanRepository.findAllByClanContentType(clanContentType);

        if (clanContentType.isLeagueWar()) {
            // 리그전 클랜 조회 시 리그전 정보는 현재 시즌 정보로 응답 구성
            assignCurrentSeasonLeagueInfo(clans);
        }

        return clans;
    }

    private void assignCurrentSeasonLeagueInfo(List<Clan> clans) {
        if (clans.isEmpty()) return;


        String season = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Set<String> clanTags = clans.stream().map(Clan::getTag).collect(Collectors.toSet());
        Map<String, ClanLeagueWarEntity> clanLeagueWarEntityMap = leagueWarRepository.findAllBySeasonAndClanTagIn(season, clanTags)
                                                                                     .stream()
                                                                                     .collect(Collectors.toMap(ClanLeagueWarEntity::getClanTag, Function.identity()));

        clans.forEach(clan -> {
            ClanLeagueWarEntity leagueWarEntity = clanLeagueWarEntityMap.get(clan.getTag());
            if (leagueWarEntity != null) {
                League warLeague = League.create(leagueWarEntity.getWarLeague());
                clan.changeWarLeague(warLeague);
            }
        });
    }

}
