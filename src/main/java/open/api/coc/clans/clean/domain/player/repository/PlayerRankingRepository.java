package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;

public interface PlayerRankingRepository {

    List<RankingHeroEquipmentDTO> findRankingHeroEquipmentByTags(List<String> playerTags);
}
