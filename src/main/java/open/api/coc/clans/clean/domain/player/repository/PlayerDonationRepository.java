package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.player.model.PlayerDonationStat;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationReceiveDTO;

public interface PlayerDonationRepository {

    PlayerDonationStat save(PlayerDonationStat playerDonationStat);

    Optional<PlayerDonationStat> findByPlayerTagAndSeason(String playerTag, String season);

    List<PlayerDonationDTO> findDonationRanking(String season, Integer pageSize);

    List<PlayerDonationReceiveDTO> findDonationReceivedRanking(String season, Integer pageSize);
}
