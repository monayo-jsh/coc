package open.api.coc.clans.clean.domain.player.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;
import open.api.coc.clans.clean.domain.player.repository.PlayerRankingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlayerRankingService {

    private final PlayerRankingRepository rankingRepository;

    public List<RankingHeroEquipmentDTO> findHeroEquipmentRanking(List<String> playerTags) {
        return rankingRepository.findRankingHeroEquipmentByTags(playerTags);
    }

}
