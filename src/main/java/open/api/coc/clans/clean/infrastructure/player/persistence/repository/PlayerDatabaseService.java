package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.repository.PlayerRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerDatabaseService implements PlayerRepository {

    private final JpaPlayerRepository jpaPlayerRepository;

    private final PlayerEntityMapper playerEntityMapper;

    @Override
    public List<Player> findAll() {
        return jpaPlayerRepository.findAll()
                                  .stream()
                                  .limit(1)
                                  .map(playerEntityMapper::toPlayer)
                                  .toList();
    }
}
