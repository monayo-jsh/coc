package open.api.coc.clans.clean.infrastructure.player.persistence.mapper;

import open.api.coc.clans.clean.domain.player.model.PlayerDonationStat;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerDonationStatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface PlayerDonationStatEntityMapper {

    @Mapping(target = "oldTroops", source = "oldDonationTroops")
    @Mapping(target = "newTroops", source = "newDonationTroops")
    @Mapping(target = "oldSpells", source = "oldDonationSpells")
    @Mapping(target = "newSpells", source = "newDonationSpells")
    @Mapping(target = "oldSieges", source = "oldDonationSieges")
    @Mapping(target = "newSieges", source = "newDonationSieges")
    PlayerDonationStatEntity toPlayerDonationStatEntity(PlayerDonationStat donationStat);

    @Mapping(target = "oldDonationTroops", source = "oldTroops")
    @Mapping(target = "newDonationTroops", source = "newTroops")
    @Mapping(target = "oldDonationSpells", source = "oldSpells")
    @Mapping(target = "newDonationSpells", source = "newSpells")
    @Mapping(target = "oldDonationSieges", source = "oldSieges")
    @Mapping(target = "newDonationSieges", source = "newSieges")
    PlayerDonationStat toPlayerDonationStat(PlayerDonationStatEntity donationStatEntity);
}
