package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfo;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEquipmentEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfoPK;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.config.HeroEquipmentConfig;
import org.springframework.stereotype.Component;

@Component
public class PlayerHeroEquipmentEntityConverter {

    public PlayerHeroEquipmentEntity convert(String playerTag, HeroEquipment source) {

        HeroEquipmentConfig heroEquipmentConfig = HeroEquipmentConfig.findByName(source.getName());

        return PlayerHeroEquipmentEntity.builder()
                                        .id(PlayerItemInfoPK.builder()
                                                            .playerTag(playerTag)
                                                            .name(source.getName())
                                                            .build())
                                        .levelInfo(PlayerItemInfo.builder()
                                                                 .level(source.getLevel())
                                                                 .maxLevel(source.getMaxLevel())
                                                                 .build())
                                        .targetHeroName(heroEquipmentConfig.getHero().getName())
                                        .wearYn(YnType.N)
                                        .build();
    }
}
