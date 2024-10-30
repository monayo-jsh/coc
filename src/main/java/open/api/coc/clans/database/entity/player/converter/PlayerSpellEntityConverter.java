package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfo;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfoPK;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerSpellEntity;
import open.api.coc.clans.clean.domain.player.config.SpellConfig;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.stereotype.Component;

@Component
public class PlayerSpellEntityConverter {

    public PlayerSpellEntity convert(String playerTag, Troops source) {
        SpellConfig spell = SpellConfig.findByName(source.getName());
        return PlayerSpellEntity.builder()
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
