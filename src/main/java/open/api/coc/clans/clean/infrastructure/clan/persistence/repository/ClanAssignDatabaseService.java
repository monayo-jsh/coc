package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanAssignedPlayer;
import open.api.coc.clans.clean.domain.clan.repository.ClanAssignRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanAssignedPlayerEntityMapper;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPK;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanAssignDatabaseService implements ClanAssignRepository {

    private final JpaClanAssignedPlayerRepository jpaClanAssignedPlayerRepository;
    private final JpaClanAssignedPlayerQueryRepository jpaClanAssignedPlayerQueryRepository;

    private final ClanAssignedPlayerEntityMapper clanAssignedPlayerEntityMapper;

    public String findLatestAssignedMonth() {
        return jpaClanAssignedPlayerQueryRepository.findLatestAssignedDate();
    }

    @Override
    public List<ClanAssignedPlayer> findAll(String assignedDate, String clanTag) {
        return jpaClanAssignedPlayerQueryRepository.findAllByAssignedDateAndClanTag(assignedDate, clanTag)
                                                   .stream()
                                                   .map(clanAssignedPlayerEntityMapper::toClanAssignedPlayer)
                                                   .toList();
    }

    @Override
    public void cancel(String seasonDate, String playerTag) {
        jpaClanAssignedPlayerRepository.deleteById(ClanAssignedPlayerPK.of(seasonDate, playerTag));
    }
}
