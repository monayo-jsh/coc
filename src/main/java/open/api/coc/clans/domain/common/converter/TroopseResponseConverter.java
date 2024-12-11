package open.api.coc.clans.domain.common.converter;

import java.util.Objects;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerSpellEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import open.api.coc.clans.clean.domain.player.config.SpellConfig;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.TroopConfig;
import open.api.coc.clans.domain.common.TroopsResponse;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TroopseResponseConverter implements Converter<Troops, TroopsResponse> {

    @Override
    public TroopsResponse convert(Troops source) {
        String type;
        Integer order;
        TroopConfig troop = TroopConfig.findByName(source.getName());
        if (Objects.equals(TroopConfig.UNKNOWN, troop)) {
            SpellConfig spell = SpellConfig.findByName(source.getName());
            type = spell.getType().name();
            order = spell.getOrder();
        } else {
            type = troop.getType().name();
            order = troop.getOrder();
        }
        return TroopsResponse.builder()
                             .name(source.getName())
                             .level(source.getLevel())
                             .maxLevel(source.getMaxLevel())
                             .village(source.getVillage())
                             .type(type)
                             .order(order)
                             .build();
    }

    public TroopsResponse convert(PlayerTroopsEntity source) {
        TroopConfig troop = TroopConfig.findByName(source.getId().getName());
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
        SpellConfig spell = SpellConfig.findByName(source.getId().getName());
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
