package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.database.entity.player.PlayerSpellEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemPKEntity;
import open.api.coc.clans.database.entity.player.common.Spell;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.stereotype.Component;

@Component
public class PlayerSpellEntityConverter {

    public PlayerSpellEntity convert(String playerTag, Troops source) {
        Spell spell = Spell.findByName(source.getName());
        return PlayerSpellEntity.builder()
                                .id(PlayerItemPKEntity.builder()
                                                      .playerTag(playerTag)
                                                      .name(source.getName())
                                                      .build())
                                .type(spell.getType())
                                .levelInfo(PlayerItemEntity.builder()
                                                           .level(source.getLevel())
                                                           .maxLevel(source.getMaxLevel())
                                                           .build())
                                .build();
    }
}
