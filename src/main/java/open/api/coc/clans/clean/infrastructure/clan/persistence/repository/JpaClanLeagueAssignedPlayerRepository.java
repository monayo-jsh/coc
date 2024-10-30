package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPK;
import open.api.coc.clans.database.entity.clan.ClanLeagueAssignedPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClanLeagueAssignedPlayerRepository extends JpaRepository<ClanLeagueAssignedPlayerEntity, ClanAssignedPlayerPK> {
}
