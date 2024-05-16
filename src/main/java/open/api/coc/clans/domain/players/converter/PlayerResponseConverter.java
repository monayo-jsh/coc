package open.api.coc.clans.domain.players.converter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.league.LeagueEntity;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.entity.player.PlayerHeroEntity;
import open.api.coc.clans.database.entity.player.PlayerHeroEquipmentEntity;
import open.api.coc.clans.database.entity.player.PlayerTroopsEntity;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.clans.domain.clans.converter.LabelResponseConverter;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;
import open.api.coc.clans.domain.common.HeroResponse;
import open.api.coc.clans.domain.common.TroopsResponse;
import open.api.coc.clans.domain.common.converter.HeroEquipmentResponseConverter;
import open.api.coc.clans.domain.common.converter.HeroResponseConverter;
import open.api.coc.clans.domain.common.converter.TroopseResponseConverter;
import open.api.coc.clans.domain.players.PlayerClanResponse;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.external.coc.clan.domain.common.PlayerClan;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.clan.domain.common.Pet;
import open.api.coc.external.coc.clan.domain.common.Troops;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class PlayerResponseConverter implements Converter<Player, PlayerResponse> {

    private final LabelResponseConverter labelResponseConverter;
    private final PlayerClanResponseConverter clanResponseConverter;
    private final HeroResponseConverter heroResponseConverter;
    private final HeroEquipmentResponseConverter heroEquipmentResponseConverter;
    private final TroopseResponseConverter troopseResponseConverter;
    @Override
    public PlayerResponse convert(Player source) {
        PlayerResponse player = PlayerResponse.builder()
                                              .name(source.getName())
                                              .tag(source.getTag())
                                              .expLevel(source.getExpLevel())
                                              .townHallLevel(source.getTownHallLevel())
                                              .trophies(source.getTrophies())
                                              .bestTrophies(source.getBestTrophies())
                                              .attackWins(source.getAttackWins())
                                              .defenseWins(source.getDefenseWins())
                                              .warStars(source.getWarStars())
                                              .warPreference(source.getWarPreference())
                                              .clan(makePlayerClan(source.getClan()))
                                              .heroes(makeHeroes(source.getHeroes()))
                                              .heroEquipments(makeHeroEquipments(source.getHeroEquipment()))
                                              .pets(makePets(source.getTroops()))
                                              .build();

        player.setHeroTotalLevel(calcHeroTotalLevel(player.getHeroes()));
        return player;
    }

    private Integer calcHeroTotalLevel(List<HeroResponse> heroes) {
        if (CollectionUtils.isEmpty(heroes)) return 0;

        return heroes.stream()
                     .filter(HeroResponse::isHomeHero)
                     .map(HeroResponse::getLevel)
                     .reduce(0, Integer::sum);

    }

    private Integer calcHeroTotalMaxLevel(List<HeroResponse> heroes) {
        if (CollectionUtils.isEmpty(heroes)) return 0;

        return heroes.stream()
                     .filter(HeroResponse::isHomeHero)
                     .map(HeroResponse::getMaxLevel)
                     .reduce(0, Integer::sum);

    }

    private PlayerClanResponse makePlayerClan(PlayerClan clan) {
        if (ObjectUtils.isEmpty(clan)) return null;
        return clanResponseConverter.convert(clan);
    }

    private List<TroopsResponse> makePets(List<Troops> troops) {
        if (CollectionUtils.isEmpty(troops)) return Collections.emptyList();

        return troops.stream()
                     .filter(troop -> Pet.isPets(troop.getName()))
                     .map(troopseResponseConverter::convert)
                     .collect(Collectors.toList());
    }

    private List<HeroEquipmentResponse> makeHeroEquipments(List<HeroEquipment> heroEquipments) {
        if (CollectionUtils.isEmpty(heroEquipments)) return Collections.emptyList();

        return heroEquipments.stream()
                             .map(heroEquipmentResponseConverter::convert)
                             .sorted(Comparator.comparingInt(heroEquipment -> Objects.requireNonNull(heroEquipment).getCode()))
                             .collect(Collectors.toList());
    }

    private List<HeroResponse> makeHeroes(List<Hero> heroes) {
        if (CollectionUtils.isEmpty(heroes)) return Collections.emptyList();

        return heroes.stream()
                     .map(heroResponseConverter::convert)
                     .sorted(Comparator.comparingInt(hero -> Objects.requireNonNull(hero).getCode()))
                     .collect(Collectors.toList());
    }













    public PlayerResponse convert(PlayerEntity source) {
        PlayerResponse player = PlayerResponse.builder()
                                              .name(source.getName())
                                              .tag(source.getPlayerTag())
                                              .expLevel(source.getExpLevel())
                                              .townHallLevel(source.getTownHallLevel())
                                              .trophies(source.getTrophies())
                                              .bestTrophies(source.getBestTrophies())
                                              .attackWins(source.getAttackWins())
                                              .defenseWins(source.getDefenseWins())
                                              .donations(source.getDonations())
                                              .donationsReceived(source.getDonationsReceived())
                                              .league(makeLeague(source.getLeague()))
                                              .warStars(source.getWarStars())
                                              .warPreference(source.getWarPreference().name())
                                              .clan(makePlayerClanResponse(source.getClan()))
                                              .heroes(makeHeroResponse(source.getHeroes()))
                                              .heroEquipments(makeHeroEquipmentResponse(source.getHeroEquipments()))
                                              .pets(makePetResponse(source.getTroops()))
                                              .build();

        player.setHeroTotalLevel(calcHeroTotalLevel(player.getHeroes()));
        player.setHeroTotalMaxLevel(calcHeroTotalMaxLevel(player.getHeroes()));
        return player;
    }

    private LabelResponse makeLeague(LeagueEntity league) {
        if (ObjectUtils.isEmpty(league)) return null;
        return labelResponseConverter.convert(league);
    }

    private PlayerClanResponse makePlayerClanResponse(ClanEntity clan) {
        if (ObjectUtils.isEmpty(clan)) return null;
        return clanResponseConverter.convert(clan);
    }

    private List<HeroResponse> makeHeroResponse(List<PlayerHeroEntity> heroes) {
        if (CollectionUtils.isEmpty(heroes)) return Collections.emptyList();

        return heroes.stream()
                     .map(heroResponseConverter::convert)
                     .sorted(Comparator.comparingInt(HeroResponse::getCode))
                     .collect(Collectors.toList());
    }

    private List<HeroEquipmentResponse> makeHeroEquipmentResponse(List<PlayerHeroEquipmentEntity> heroEquipments) {
        if (CollectionUtils.isEmpty(heroEquipments)) return Collections.emptyList();

        return heroEquipments.stream()
                             .map(heroEquipmentResponseConverter::convert)
                             .sorted(Comparator.comparingInt(HeroEquipmentResponse::getCode))
                             .collect(Collectors.toList());
    }

    private List<TroopsResponse> makePetResponse(List<PlayerTroopsEntity> troops) {
        if (CollectionUtils.isEmpty(troops)) return Collections.emptyList();

        return troops.stream()
                     .filter(PlayerTroopsEntity::isPet)
                     .map(troopseResponseConverter::convert)
                     .collect(Collectors.toList());
    }
}
