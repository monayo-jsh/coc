package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaClanGameRepository extends JpaRepository<ClanGameEntity, Long> {

    @Query(
        value = "select new open.api.coc.clans.clean.domain.clan.model.ClanGameDTO(clanGame.progressDate, clanGame.playerTag, player.name, clanGame.finishPoint - clanGame.startPoint, clanGame.lastModifiedAt) "
            + "from ClanGameEntity clanGame "
            + "join PlayerEntity player on player.playerTag = clanGame.playerTag "
            + "where clanGame.progressDate = :progressDate "
            + "and clanGame.finishPoint > clanGame.startPoint "
    )
    List<ClanGameDTO> findAllByProgressDate(String progressDate);

    Optional<ClanGameEntity> findByPlayerTagAndProgressDate(String playerTag, String progressDate);

}
