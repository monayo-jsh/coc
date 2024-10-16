package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfo;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfoPK;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.TroopConfig;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.stereotype.Component;

@Component
public class PlayerTroopEntityConverter {

    public PlayerTroopsEntity convert(String playerTag, Troops source) {
        TroopConfig troop = TroopConfig.findByName(source.getName());
        return PlayerTroopsEntity.builder()
                                 .id(PlayerItemInfoPK.builder()
                                                     .playerTag(playerTag)
                                                     .name(source.getName())
                                                     .build())
                                 .type(troop.getType())
                                 .levelInfo(PlayerItemInfo.builder()
                                                          .level(source.getLevel())
                                                          .maxLevel(source.getMaxLevel())
                                                          .build())
                                 .build();
    }
}
