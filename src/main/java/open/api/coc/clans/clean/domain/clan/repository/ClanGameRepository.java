package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.ClanGame;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanGameMeta;

public interface ClanGameRepository {

    Optional<ClanGameMeta> findClanGameMetaByProgressDate(String progressDate);
    List<ClanGameDTO> findAllByProgressDate(String progressDate);
    Optional<ClanGame> findByPlayerTagAndProgressDate(String playerTag, String progressDate);

    ClanGame save(ClanGame newClanGame);
}
