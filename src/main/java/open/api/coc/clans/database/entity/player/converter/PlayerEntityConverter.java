package open.api.coc.clans.database.entity.player.converter;

import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.entity.player.common.WarPreferenceType;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class PlayerEntityConverter implements Converter<Player, PlayerEntity> {

    @Override
    public @NonNull PlayerEntity convert(Player source) {

        return PlayerEntity.builder()
                           .name(source.getName())
                           .playerTag(source.getTag())
                           .role(source.getRole())
                           .expLevel(source.getExpLevel())
                           .townHallLevel(source.getTownHallLevel())
                           .trophies(source.getTrophies())
                           .bestTrophies(source.getBestTrophies())
                           .donations(source.getDonations())
                           .donationsReceived(source.getDonationsReceived())
                           .attackWins(source.getAttackWins())
                           .defenseWins(source.getDefenseWins())
                           .warStars(source.getWarStars())
                           .warPreference(makeWarPreferenceType(source.getWarPreference()))
                           .build();

    }

    private WarPreferenceType makeWarPreferenceType(String warPreference) {
        if (ObjectUtils.isEmpty(warPreference)) return WarPreferenceType.out;
        return WarPreferenceType.valueOf(warPreference);
    }

}
