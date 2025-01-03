package open.api.coc.external.coc.clan;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.stereotype.Component;

@Component
public interface ClanApiService {

    Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag);

    Optional<Player> findPlayerBy(String playerTag);

    Optional<LabelList> findLeagues();

    List<LinkedHashMap<String,List<String>>> findClanWarLeagueRoundTags(String clanTag) throws JsonProcessingException;
    Optional<ClanWar> findLeagueWarByRoundTag(String roundTag);

    Optional<ClanCurrentWarLeagueGroup> findClanCurrentWarLeagueGroupBy(String clanTag);
    Optional<ClanWar> findWarLeagueByWarTag(String warTag);

}
