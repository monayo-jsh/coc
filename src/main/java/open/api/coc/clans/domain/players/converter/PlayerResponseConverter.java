package open.api.coc.clans.domain.players.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.common.HeroResponse;
import open.api.coc.clans.domain.common.converter.HeroResponseConverter;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class PlayerResponseConverter implements Converter<Player, PlayerResponse> {

    private final HeroResponseConverter heroResponseConverter;

    @Override
    public PlayerResponse convert(Player source) {
        return PlayerResponse.builder()
                             .name(source.getName())
                             .tag(source.getTag())
                             .expLevel(source.getExpLevel())
                             .townHallLevel(source.getTownHallLevel())
                             .trophies(source.getTrophies())
                             .bestTrophies(source.getBestTrophies())
                             .attackWins(source.getAttackWins())
                             .defenseWins(source.getDefenseWins())
                             .warStars(source.getWarStars())
                             .heroes(makeHeroes(source.getHeroes()))
                             .build();
    }

    private List<HeroResponse> makeHeroes(List<Hero> heroes) {
        if (CollectionUtils.isEmpty(heroes)) return Collections.emptyList();

        return heroes.stream()
                     .map(heroResponseConverter::convert)
                     .collect(Collectors.toList());
    }

}
