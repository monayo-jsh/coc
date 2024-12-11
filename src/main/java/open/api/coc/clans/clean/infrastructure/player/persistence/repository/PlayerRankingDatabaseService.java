package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;
import open.api.coc.clans.clean.domain.player.repository.PlayerRankingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerRankingDatabaseService implements PlayerRankingRepository {

    private final JpaPlayerHeroEquipmentCustomRepository heroEquipmentCustomRepository;

    @Override
    public List<RankingHeroEquipmentDTO> findRankingHeroEquipmentByTags(List<String> playerTags) {
        return heroEquipmentCustomRepository.findRankingHeroEquipmentByTags(playerTags);
    }
}
