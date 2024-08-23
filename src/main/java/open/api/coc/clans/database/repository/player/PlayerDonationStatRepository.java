package open.api.coc.clans.database.repository.player;

import open.api.coc.clans.database.entity.player.PlayerDonationStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDonationStatRepository extends JpaRepository<PlayerDonationStatEntity, String> {
}
