package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerDonationStat;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerDonationStatEntity;
import org.mapstruct.Mapper;

@Mapper(
    config = MapStructConfig.class
)
public interface PlayerDonationStatEntityMapper {

    PlayerDonationStatEntity toPlayerDonationStatEntity(PlayerDonationStat donationStat);

    PlayerDonationStat toPlayerDonationStat(PlayerDonationStatEntity donationStatEntity);
}
