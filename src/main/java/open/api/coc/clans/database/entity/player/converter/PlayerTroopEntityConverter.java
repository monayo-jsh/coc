package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.database.entity.player.PlayerTroopsEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemPKEntity;
import open.api.coc.clans.database.entity.player.common.Troop;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.stereotype.Component;

@Component
public class PlayerTroopEntityConverter {

    public PlayerTroopsEntity convert(String playerTag, Troops source) {
        Troop troop = Troop.findByName(source.getName());
        return PlayerTroopsEntity.builder()
                                 .id(PlayerItemPKEntity.builder()
                                                       .playerTag(playerTag)
                                                       .name(source.getName())
                                                       .build())
                                 .type(troop.getType())
                                 .levelInfo(PlayerItemEntity.builder()
                                                            .level(source.getLevel())
                                                            .maxLevel(source.getMaxLevel())
                                                            .build())
                                 .build();
    }
}
