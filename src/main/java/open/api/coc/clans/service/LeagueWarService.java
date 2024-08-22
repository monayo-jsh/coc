package open.api.coc.clans.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanLeagueWarEntity;
import open.api.coc.clans.database.repository.clan.ClanLeagueWarQueryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeagueWarService {

    private final ClanLeagueWarQueryRepository clanLeagueWarQueryRepository;

    public void assignCurrentSeasonLeagueInfo(List<ClanEntity> leagueWarClans) {
        if (leagueWarClans.isEmpty()) return;

        // 현재 시즌 리그 정보 설정
        String season = getCurrentSeason();
        Map<String, ClanLeagueWarEntity> clanLeagueWarEntityMap = findClanLeagueWarEntityMap(season);

        for(ClanEntity clanEntity : leagueWarClans) {
            ClanLeagueWarEntity clanLeagueWarEntity = clanLeagueWarEntityMap.get(clanEntity.getTag());
            if (clanLeagueWarEntity != null) {
                clanEntity.changeWarLeague(clanLeagueWarEntity.getWarLeague());
            }
        }
    }

    private String getCurrentSeason() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    private Map<String, ClanLeagueWarEntity> findClanLeagueWarEntityMap(String season) {
        return clanLeagueWarQueryRepository.findAllBySeason(season)
                                           .stream()
                                           .collect(Collectors.toMap(ClanLeagueWarEntity::getClanTag, entity -> entity));
    }
}
