package open.api.coc.clans.clean.domain.player.repository;

import java.util.Optional;
import open.api.coc.clans.clean.domain.player.model.PlayerDonationStat;

public interface PlayerDonationRepository {

    PlayerDonationStat save(PlayerDonationStat playerDonationStat);

    Optional<PlayerDonationStat> findByPlayerTagAndSeason(String playerTag, String season);

}
