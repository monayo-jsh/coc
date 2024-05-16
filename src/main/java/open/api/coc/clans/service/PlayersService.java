package open.api.coc.clans.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.common.exception.handler.ExceptionHandler;
import open.api.coc.clans.database.entity.clan.ClanBadgeEntity;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.converter.ClanEntityConverter;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.common.converter.IconUrlEntityConverter;
import open.api.coc.clans.database.entity.league.LeagueEntity;
import open.api.coc.clans.database.entity.league.converter.LeagueEntityConverter;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.entity.player.PlayerHeroEquipmentEntity;
import open.api.coc.clans.database.entity.player.converter.PlayerEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerHeroEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerHeroEquipmentEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerSpellEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerTroopEntityConverter;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.common.LeagueRepository;
import open.api.coc.clans.database.repository.player.PlayerRepository;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.domain.players.converter.PlayerResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.clan.domain.common.PlayerClan;
import open.api.coc.external.coc.clan.domain.common.Troops;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayersService {

    private final ClanApiService clanApiService;

    private final LeagueRepository leagueRepository;
    private final ClanRepository clanRepository;
    private final PlayerRepository playerRepository;

    private final PlayerResponseConverter playerResponseConverter;

    private final LeagueEntityConverter leagueEntityConverter;

    private final ClanEntityConverter clanEntityConverter;

    private final IconUrlEntityConverter iconUrlEntityConverter;
    private final PlayerEntityConverter playerEntityConverter;
    private final PlayerHeroEntityConverter playerHeroEntityConverter;
    private final PlayerHeroEquipmentEntityConverter playerHeroEquipmentEntityConverter;
    private final PlayerTroopEntityConverter playerTroopEntityConverter;
    private final PlayerSpellEntityConverter playerSpellEntityConverter;

    public PlayerResponse findPlayerBy(String playerTag) {
        Player player = clanApiService.fetchPlayerBy(playerTag)
                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "플레이어 조회 실패"));

        return playerResponseConverter.convert(player);

    }

    public List<PlayerResponse> findPlayerBy(List<String> playerTags) {
        // 조회 성공한 목록만 반환
        return playerTags.stream()
                         .map(clanApiService::findPlayerBy)
                         .filter(Optional::isPresent)
                         .map(player -> playerResponseConverter.convert(player.get()))
                         .toList();
    }

    @Transactional
    public void registerPlayer(String playerTag) {
        Player player = clanApiService.fetchPlayerBy(playerTag)
                                      .orElseThrow(() -> ExceptionHandler.createNotFoundException("%s 조회 실패".formatted(playerTag)));

        PlayerEntity playerEntity = playerEntityConverter.convert(player);

        createPlayerHeroEntities(playerEntity, player);
        createPlayerHeroEquipmentEntities(playerEntity, player);
        createPlayerTroopsEntities(playerEntity, player);
        createPlayerSpellEntities(playerEntity, player);

        createLeague(playerEntity, player);
        createClan(playerEntity, player);

        playerRepository.save(playerEntity);
    }

    private void createClan(PlayerEntity playerEntity, Player player) {
        if (Objects.isNull(player.getClan())) return;

        ClanEntity clan = clanRepository.findById(player.getClan().getTag()).orElse(null);
        if (Objects.isNull(clan)) {
            clan = createClan(player.getClan());
        }

        playerEntity.changeClan(clan);
    }

    private ClanEntity createClan(PlayerClan clan) {
        ClanEntity createClanEntity = clanEntityConverter.convert(clan);
        createClanEntity.changeClanContent(ClanContentEntity.empty(createClanEntity.getTag()));
        ClanBadgeEntity clanBadgeEntity = ClanBadgeEntity.builder()
                                                         .tag(createClanEntity.getTag())
                                                         .iconUrl(iconUrlEntityConverter.convert(clan.getBadgeUrls()))
                                                         .build();

        createClanEntity.changeBadgeUrl(clanBadgeEntity);
        return clanRepository.save(createClanEntity);
    }

    private void createLeague(PlayerEntity playerEntity, Player player) {
        if (Objects.isNull(player.getLeague())) return;

        LeagueEntity league = leagueRepository.findById(player.getLeague().getId())
                                              .orElseGet(() -> leagueEntityConverter.convert(player.getLeague()));

        playerEntity.changeLeague(league);
    }

    private void createPlayerHeroEntities(PlayerEntity playerEntity, Player player) {
        playerEntity.changeHeroes(player.getHeroes()
                                        .stream()
                                        .filter(Hero::isVillageHome)
                                        .map(hero -> playerHeroEntityConverter.convert(player.getTag(), hero))
                                        .collect(Collectors.toList()));
    }

    private void createPlayerHeroEquipmentEntities(PlayerEntity playerEntity, Player player) {
        List<PlayerHeroEquipmentEntity> playerHeroEquipmentEntities = player.getHeroEquipment()
                                                                            .stream()
                                                                            .filter(HeroEquipment::isVillageHome)
                                                                            .map(heroEquipment -> playerHeroEquipmentEntityConverter.convert(player.getTag(), heroEquipment))
                                                                            .collect(Collectors.toList());

        for (Hero hero : player.getHeroes()) {
            if (hero.isNotVillageHome()) continue;


            for (HeroEquipment heroEquipment : hero.getEquipment()) {
                PlayerHeroEquipmentEntity findPlayerHeroEquipmentEntity = playerHeroEquipmentEntities.stream()
                                                                                                  .filter(playerHeroEquipmentEntity -> playerHeroEquipmentEntity.isEqualsHeroEquipmentName(heroEquipment.getName()))
                                                                                                  .findFirst()
                                                                                                  .orElse(null);

                // NotFound Hero Equipment..
                if (Objects.isNull(findPlayerHeroEquipmentEntity)) continue;

                findPlayerHeroEquipmentEntity.setWearYn(YnType.Y);
            }
        }
        playerEntity.changeHeroEquipments(playerHeroEquipmentEntities);
    }

    private void createPlayerTroopsEntities(PlayerEntity playerEntity, Player player) {
        playerEntity.changeTroops(player.getTroops()
                                        .stream()
                                        .filter(Troops::isVillageHome)
                                        .map(troops -> playerTroopEntityConverter.convert(player.getTag(), troops))
                                        .collect(Collectors.toList()));
    }

    private void createPlayerSpellEntities(PlayerEntity playerEntity, Player player) {
        playerEntity.changeSpells(player.getSpells()
                                        .stream()
                                        .filter(Troops::isVillageHome)
                                        .map(spell -> playerSpellEntityConverter.convert(player.getTag(), spell))
                                        .collect(Collectors.toList()));
    }

    public void deletePlayer(String playerTag) {
        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isEmpty()) {
            return;
        }

        playerRepository.delete(findPlayer.get());
    }
}
