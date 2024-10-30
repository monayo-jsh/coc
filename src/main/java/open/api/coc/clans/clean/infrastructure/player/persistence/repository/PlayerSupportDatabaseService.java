package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.repository.PlayerSupportRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerSupportDatabaseService implements PlayerSupportRepository {

    private final JpaPlayerCustomRepository customRepository;

    @Override
    public void resetAll() {
        customRepository.resetAllSupportType();
    }

    @Override
    public long update(List<String> playerTags) {
        return customRepository.updateSupport(playerTags);
    }

}
