package open.api.coc.clans.service;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createBadRequestException;
import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import open.api.coc.clans.clean.infrastructure.league.persistence.repository.JpaLeagueRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEquipmentEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerSpellEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.repository.JpaPlayerRecordRepository;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.clan.ClanBadgeEntity;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.converter.ClanEntityConverter;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.common.converter.IconUrlEntityConverter;
import open.api.coc.clans.database.entity.league.converter.LeagueEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerHeroEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerHeroEquipmentEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerSpellEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerTroopEntityConverter;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.player.PlayerQueryRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayersService {

    private final ClanApiService clanApiService;

    private final JpaLeagueRepository leagueRepository;

    private final ClanRepository clanRepository;

    private final PlayerRepository playerRepository;
    private final PlayerQueryRepository playerQueryRepository;

    private final JpaPlayerRecordRepository playerRecordRepository;
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
        Player player = clanApiService.findPlayerBy(playerTag)
                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "플레이어 조회 실패"));

        return playerResponseConverter.convert(player);
    }

    public List<PlayerResponse> findPlayerBy(List<String> playerTags) {
        // 조회 성공한 목록만 반환
        return playerTags.stream()
                         .map(playerRepository::findById)
                         .filter(Optional::isPresent)
                         .map(player -> playerResponseConverter.convert(player.get()))
                         .toList();
    }

    @Transactional(readOnly = true)
    public List<PlayerEntity> findAllWithoutRecordTarget() {
        return playerQueryRepository.findAllWithoutRecordTarget();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PlayerResponse registerPlayer(String playerTag) {

        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isPresent()) {
            throw createBadRequestException(ExceptionCode.ALREADY_DATA.getCode(), "이미 등록된 클랜원");
        }

        PlayerEntity playerEntity = createPlayerEntity(playerTag);

        PlayerEntity createdPlayer = playerRepository.save(playerEntity);
        return playerResponseConverter.convert(createdPlayer);
    }

    private PlayerEntity createPlayerEntity(String playerTag) {
        Player player = clanApiService.findPlayerBy(playerTag)
                                      .orElseThrow(() -> createNotFoundException("%s 조회 실패".formatted(playerTag)));

        PlayerEntity playerEntity = playerEntityConverter.convert(player);

        createPlayerHeroEntities(playerEntity, player);
        createPlayerHeroEquipmentEntities(playerEntity, player);
        createPlayerTroopsEntities(playerEntity, player);
        createPlayerSpellEntities(playerEntity, player);

        createLeague(playerEntity, player);
        createClan(playerEntity, player);
        return playerEntity;
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
        List<PlayerHeroEntity> playerHeroEntities = makePlayerHeroEntities(playerEntity.getPlayerTag(), player.getHeroes());
        playerEntity.changeHeroes(playerHeroEntities);
    }

    private List<PlayerHeroEntity> makePlayerHeroEntities(String playerTag, List<Hero> heroes) {
        return heroes.stream()
                     .filter(Hero::isVillageHome)
                     .map(hero -> playerHeroEntityConverter.convert(playerTag, hero))
                     .collect(Collectors.toList());

    }

    private void createPlayerHeroEquipmentEntities(PlayerEntity playerEntity, Player player) {
        List<PlayerHeroEquipmentEntity> playerHeroEquipmentEntities = makePlayerHeroEquipmentEntities(playerEntity.getPlayerTag(), player.getHeroEquipment());
        convertHeroEquipmentWearYn(player.getHeroes(), playerHeroEquipmentEntities);
        playerEntity.changeHeroEquipments(playerHeroEquipmentEntities);
    }

    private void convertHeroEquipmentWearYn(List<Hero> heroes, List<PlayerHeroEquipmentEntity> playerHeroEquipmentEntities) {
        Map<String, PlayerHeroEquipmentEntity> playerHeroEquipmentEntityMap = playerHeroEquipmentEntitiesToMap(playerHeroEquipmentEntities);
        for (Hero hero : heroes) {
            if (hero.isNotVillageHome()) continue;
            if (CollectionUtils.isEmpty(hero.getEquipment())) continue;

            for (HeroEquipment heroEquipment : hero.getEquipment()) {
                PlayerHeroEquipmentEntity findPlayerHeroEquipmentEntity = playerHeroEquipmentEntityMap.get(heroEquipment.getName());

                // NotFound Hero Equipment..
                if (Objects.isNull(findPlayerHeroEquipmentEntity)) continue;

                findPlayerHeroEquipmentEntity.setWearYn(YnType.Y);
            }
        }
    }

    private List<PlayerHeroEquipmentEntity> makePlayerHeroEquipmentEntities(String playerTag, List<HeroEquipment> heroEquipments) {
        return heroEquipments.stream()
                             .filter(HeroEquipment::isVillageHome)
                             .map(heroEquipment -> playerHeroEquipmentEntityConverter.convert(playerTag, heroEquipment))
                             .collect(Collectors.toList());

    }

    private void createPlayerTroopsEntities(PlayerEntity playerEntity, Player player) {
        List<PlayerTroopsEntity> playerTroopsEntities = makePlayerTroopsEntities(playerEntity.getPlayerTag(), player.getTroops());
        playerEntity.changeTroops(playerTroopsEntities);
    }

    private List<PlayerTroopsEntity> makePlayerTroopsEntities(String playerTag, List<Troops> troops) {
        return troops.stream()
                     .filter(Troops::isVillageHome)
                     .map(troop -> playerTroopEntityConverter.convert(playerTag, troop))
                     .collect(Collectors.toList());

    }

    private void createPlayerSpellEntities(PlayerEntity playerEntity, Player player) {
        List<PlayerSpellEntity> playerSpellEntities = makePlayerSpellEntities(playerEntity.getPlayerTag(), player.getSpells());
        playerEntity.changeSpells(playerSpellEntities);
    }

    private List<PlayerSpellEntity> makePlayerSpellEntities(String playerTag, List<Troops> spells) {
        return spells.stream()
                     .filter(Troops::isVillageHome)
                     .map(spell -> playerSpellEntityConverter.convert(playerTag, spell))
                     .collect(Collectors.toList());
    }



    private Map<String, PlayerHeroEquipmentEntity> playerHeroEquipmentEntitiesToMap(List<PlayerHeroEquipmentEntity> playerHeroEquipmentEntities) {
        return playerHeroEquipmentEntities.stream()
                                          .collect(Collectors.toMap((playerHeroEquipmentEntity -> playerHeroEquipmentEntity.getId()
                                                                                                                           .getName()), playerHeroEquipmentEntity -> playerHeroEquipmentEntity));

    }

    public List<String> findAllPlayerTags() {
        return playerRepository.findAllPlayerTag();
    }


    public List<PlayerEntity> findAllPlayersBy(List<String> playerTags) {
        return playerRepository.findAllById(playerTags);
    }


    @Transactional(readOnly = true)
    public List<String> findAllPlayersToRecord() {
        return playerRecordRepository.findAll()
                                     .stream()
                                     .map(PlayerRecordEntity::getTag)
                                     .toList();
    }

}
