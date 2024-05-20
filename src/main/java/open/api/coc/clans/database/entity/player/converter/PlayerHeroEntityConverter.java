package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.database.entity.player.PlayerHeroEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemPKEntity;
import open.api.coc.external.coc.clan.domain.common.Hero;
import org.springframework.stereotype.Component;

@Component
public class PlayerHeroEntityConverter {

    public PlayerHeroEntity convert(String playerTag, Hero source) {
        return PlayerHeroEntity.builder()
                               .id(PlayerItemPKEntity.builder()
                                                     .playerTag(playerTag)
                                                     .name(source.getName())
                                                     .build())
                               .levelInfo(PlayerItemEntity.builder()
                                                          .level(source.getLevel())
                                                          .maxLevel(source.getMaxLevel())
                                                          .build())
                               .build();
    }
}
