package open.api.coc.external.coc.clan;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.*;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.stereotype.Component;

@Component
public interface ClanApiService {

    Optional<Clan> findClanByClanTag(String clanTag);

    Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit);

    Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag);

    Optional<ClanMemberList> findClanMembersByClanTag(String clanTag);

    Optional<Player> findPlayerBy(String playerTag);

    Optional<Player> fetchPlayerBy(String playerTag);
    Optional<LabelList> findLeagues();

    List<LinkedHashMap<String,List<String>>> findClanWarLeagueRoundTags(String clanTag) throws JsonProcessingException;
    Optional<ClanWar> findLeagueWarByRoundTag(String roundTag);

    Optional<ClanCurrentWarLeagueGroup> findClanCurrentWarLeagueGroupBy(String clanTag);

}
