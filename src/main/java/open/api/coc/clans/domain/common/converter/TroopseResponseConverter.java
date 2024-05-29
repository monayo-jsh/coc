package open.api.coc.clans.domain.common.converter;

import open.api.coc.clans.database.entity.player.PlayerSpellEntity;
import open.api.coc.clans.database.entity.player.PlayerTroopsEntity;
import open.api.coc.clans.database.entity.player.common.Spell;
import open.api.coc.clans.database.entity.player.common.Troop;
import open.api.coc.clans.domain.common.TroopsResponse;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TroopseResponseConverter implements Converter<Troops, TroopsResponse> {

    @Override
    public TroopsResponse convert(Troops source) {
        Troop troop = Troop.findByName(source.getName());
        return TroopsResponse.builder()
                             .name(source.getName())
                             .level(source.getLevel())
                             .maxLevel(source.getMaxLevel())
                             .village(source.getVillage())
                             .type(troop.getType().name())
                             .order(troop.getOrder())
                             .build();
    }

    public TroopsResponse convert(PlayerTroopsEntity source) {
        Troop troop = Troop.findByName(source.getId().getName());
        return TroopsResponse.builder()
                             .name(troop.getName())
                             .level(source.getLevelInfo().getLevel())
                             .maxLevel(source.getLevelInfo().getMaxLevel())
                             .village("home")
                             .type(troop.getType().name())
                             .order(troop.getOrder())
                             .build();
    }

    public TroopsResponse convert(PlayerSpellEntity source) {
        Spell spell = Spell.findByName(source.getId().getName());
        return TroopsResponse.builder()
                             .name(spell.getName())
                             .level(source.getLevelInfo().getLevel())
                             .maxLevel(source.getLevelInfo().getMaxLevel())
                             .village("home")
                             .type(spell.getType().name())
                             .order(spell.getOrder())
                             .build();
    }
}
