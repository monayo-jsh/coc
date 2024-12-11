package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfo;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfoPK;
import open.api.coc.external.coc.clan.domain.common.Hero;
import org.springframework.stereotype.Component;

@Component
public class PlayerHeroEntityConverter {

    public PlayerHeroEntity convert(String playerTag, Hero source) {
        return PlayerHeroEntity.builder()
                               .id(PlayerItemInfoPK.builder()
                                                   .playerTag(playerTag)
                                                   .name(source.getName())
                                                   .build())
                               .levelInfo(PlayerItemInfo.builder()
                                                        .level(source.getLevel())
                                                        .maxLevel(source.getMaxLevel())
                                                        .build())
                               .build();
    }
}
