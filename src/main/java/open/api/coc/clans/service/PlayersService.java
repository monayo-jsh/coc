package open.api.coc.clans.service;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createBadRequestException;
import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.config.HallOfFameConfig;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPKEntity;
import open.api.coc.clans.database.entity.clan.ClanBadgeEntity;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.converter.ClanEntityConverter;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.common.converter.IconUrlEntityConverter;
import open.api.coc.clans.database.entity.league.LeagueEntity;
import open.api.coc.clans.database.entity.league.converter.LeagueEntityConverter;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.entity.player.PlayerHeroEntity;
import open.api.coc.clans.database.entity.player.PlayerHeroEquipmentEntity;
import open.api.coc.clans.database.entity.player.PlayerSpellEntity;
import open.api.coc.clans.database.entity.player.PlayerTroopsEntity;
import open.api.coc.clans.database.entity.player.common.WarPreferenceType;
import open.api.coc.clans.database.entity.player.converter.PlayerEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerHeroEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerHeroEquipmentEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerSpellEntityConverter;
import open.api.coc.clans.database.entity.player.converter.PlayerTroopEntityConverter;
import open.api.coc.clans.database.repository.clan.ClanAssignedPlayerQueryRepository;
import open.api.coc.clans.database.repository.clan.ClanAssignedPlayerRepository;
import open.api.coc.clans.database.repository.clan.ClanLeagueAssignedPlayerRepository;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.common.LeagueRepository;
import open.api.coc.clans.database.repository.player.PlayerQueryRepository;
import open.api.coc.clans.database.repository.player.PlayerRepository;
import open.api.coc.clans.domain.players.PlayerModify;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.domain.players.RankingHeroEquipmentResponse;
import open.api.coc.clans.domain.players.converter.PlayerResponseConverter;
import open.api.coc.clans.domain.players.converter.RankingHeroEquipmentResponseConverter;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.clan.domain.common.Label;
import open.api.coc.external.coc.clan.domain.common.PlayerClan;
import open.api.coc.external.coc.clan.domain.common.Troops;
import open.api.coc.external.coc.clan.domain.player.Player;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayersService {

    private final HallOfFameConfig hallOfFameConfig;

    private final ClanApiService clanApiService;

    private final LeagueRepository leagueRepository;

    private final ClanRepository clanRepository;
    private final ClanAssignedPlayerRepository clanAssignedPlayerRepository;
    private final ClanAssignedPlayerQueryRepository clanAssignedPlayerQueryRepository;

    private final ClanLeagueAssignedPlayerRepository clanLeagueAssignedPlayerRepository;

    private final PlayerRepository playerRepository;

    private final PlayerQueryRepository playerQueryRepository;

    private final PlayerResponseConverter playerResponseConverter;

    private final LeagueEntityConverter leagueEntityConverter;

    private final ClanEntityConverter clanEntityConverter;

    private final IconUrlEntityConverter iconUrlEntityConverter;
    private final PlayerEntityConverter playerEntityConverter;
    private final PlayerHeroEntityConverter playerHeroEntityConverter;
    private final PlayerHeroEquipmentEntityConverter playerHeroEquipmentEntityConverter;
    private final PlayerTroopEntityConverter playerTroopEntityConverter;
    private final PlayerSpellEntityConverter playerSpellEntityConverter;

    private final RankingHeroEquipmentResponseConverter rankingHeroEquipmentResponseConverter;

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

    public List<PlayerEntity> findAllPlayerEntities() {
        return playerQueryRepository.findAll();
    }

    public List<PlayerResponse> findAllPlayers() {
        List<PlayerEntity> players = playerQueryRepository.findAll();

        return players.stream()
                      .map(playerResponseConverter::convertAll)
                      .collect(Collectors.toList());
    }

    public List<PlayerResponse> findAllPlayersSummary() {
        List<PlayerEntity> players = playerQueryRepository.findAll();

        return players.stream()
                      .map(playerResponseConverter::convert)
                      .collect(Collectors.toList());
    }

    public List<PlayerResponse> findPlayersSummary(String name) {
        List<PlayerEntity> players = playerQueryRepository.findAllByName(name);

        return players.stream()
                      .map(playerResponseConverter::convert)
                      .collect(Collectors.toList());
    }

    public List<PlayerResponse> findAllSupportPlayers() {
        List<PlayerEntity> players = playerRepository.findAllBySupportYn(YnType.Y);

        return players.stream()
                      .map(playerResponseConverter::convert)
                      .collect(Collectors.toList());
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

    @Transactional
    public boolean updatePlayer(String playerTag) {
        PlayerEntity playerEntity = playerRepository.findById(playerTag)
                                                    .orElseThrow(() -> createNotFoundException("%s 조회 실패".formatted(playerTag)));

        Player player = clanApiService.findPlayerBy(playerTag)
                                      .orElseThrow(() -> createNotFoundException("%s 조회 실패".formatted(playerTag)));

        modifyPlayer(playerEntity, player);
        modifyPlayerHero(playerEntity, player.getHeroes());
        modifyPlayerHeroEquipment(playerEntity, player);
        modifyPlayerTroops(playerEntity, player.getTroops());
        modifyPlayerSpells(playerEntity, player.getSpells());

        modifyLeague(playerEntity, player.getLeague());
        modifyClan(playerEntity, player.getClan());

        playerRepository.save(playerEntity);

        return true;
    }

    private void modifyClan(PlayerEntity player, PlayerClan playerClan) {
        if (Objects.isNull(playerClan)) {
            player.setClan(null);
            return;
        }

        ClanEntity clan = clanRepository.findById(playerClan.getTag()).orElse(null);
        if (Objects.isNull(clan)) {
            clan = createClan(playerClan);
        }

        player.changeClan(clan);
    }

    private void modifyLeague(PlayerEntity playerEntity, Label league) {
        if (Objects.isNull(league)) {
            playerEntity.setLeague(null);
            return;
        }

        playerEntity.changeLeague(leagueEntityConverter.convert(league));
    }

    private void modifyPlayerSpells(PlayerEntity playerEntity, List<Troops> spells) {
        Map<String, PlayerSpellEntity> dbPlayerSpellEntityMap = playerSpellEntityToMap(playerEntity.getSpells());
        Map<String, PlayerSpellEntity> realPlayerSpellEntitiyMap = playerSpellEntityToMap(makePlayerSpellEntities(playerEntity.getPlayerTag(), spells));

        for (String key : realPlayerSpellEntitiyMap.keySet()) {
            PlayerSpellEntity dbPlayerSpellEntity = dbPlayerSpellEntityMap.get(key);
            PlayerSpellEntity realPlayerSpellEntity = realPlayerSpellEntitiyMap.get(key);
            if (Objects.isNull(dbPlayerSpellEntity)) {
                playerEntity.addSpell(realPlayerSpellEntity);
                continue;
            }

            dbPlayerSpellEntity.setType(realPlayerSpellEntity.getType());
            dbPlayerSpellEntity.setLevelInfo(realPlayerSpellEntity.getLevelInfo());
        }
    }

    private void modifyPlayerTroops(PlayerEntity playerEntity, List<Troops> troops) {
        Map<String, PlayerTroopsEntity> dbPlayerTroopsEntityMap = playerTroopEntitiesToMap(playerEntity.getTroops());
        Map<String, PlayerTroopsEntity> realPlayerTroopsEntitiyMap = playerTroopEntitiesToMap(makePlayerTroopsEntities(playerEntity.getPlayerTag(), troops));

        for (String key : realPlayerTroopsEntitiyMap.keySet()) {
            PlayerTroopsEntity dbPlayerTroopsEntity = dbPlayerTroopsEntityMap.get(key);
            PlayerTroopsEntity realPlayerTroopsEntity = realPlayerTroopsEntitiyMap.get(key);
            if (Objects.isNull(dbPlayerTroopsEntity)) {
                playerEntity.addTroop(realPlayerTroopsEntity);
                continue;
            }

            dbPlayerTroopsEntity.setLevelInfo(realPlayerTroopsEntity.getLevelInfo());
        }
    }

    private void modifyPlayerHeroEquipment(PlayerEntity playerEntity, Player player) {
        Map<String, PlayerHeroEquipmentEntity> dbPlayerHeroEquipmentEntityMap = playerHeroEquipmentEntitiesToMap(playerEntity.getHeroEquipments());
        List<PlayerHeroEquipmentEntity> playerHeroEquipmentEntities = makePlayerHeroEquipmentEntities(playerEntity.getPlayerTag(), player.getHeroEquipment());
        Map<String, PlayerHeroEquipmentEntity> realPlayerHeroEntityMap = playerHeroEquipmentEntitiesToMap(playerHeroEquipmentEntities);

        for (String key : realPlayerHeroEntityMap.keySet()) {
            PlayerHeroEquipmentEntity dbPlayerHeroEntity = dbPlayerHeroEquipmentEntityMap.get(key);
            PlayerHeroEquipmentEntity playerHeroEntity = realPlayerHeroEntityMap.get(key);
            if (Objects.isNull(dbPlayerHeroEntity)) {
                playerEntity.addHeroEquipment(playerHeroEntity);
                continue;
            }

            dbPlayerHeroEntity.setLevelInfo(playerHeroEntity.getLevelInfo());
            dbPlayerHeroEntity.setTargetHeroName(playerHeroEntity.getTargetHeroName());
        }

        convertHeroEquipmentWearYn(player.getHeroes(), playerHeroEquipmentEntities);
        playerEntity.changeHeroEquipments(playerHeroEquipmentEntities);
    }

    private void modifyPlayerHero(PlayerEntity playerEntity, List<Hero> heroes) {
        Map<String, PlayerHeroEntity> dbPlayerHeroEntityMap = playerHeroEntitiesToMap(playerEntity.getHeroes());
        Map<String, PlayerHeroEntity> realPlayerHeroEntityMap = playerHeroEntitiesToMap(makePlayerHeroEntities(playerEntity.getPlayerTag(), heroes));

        for (String key : realPlayerHeroEntityMap.keySet()) {
            PlayerHeroEntity dbPlayerHeroEntity = dbPlayerHeroEntityMap.get(key);
            PlayerHeroEntity playerHeroEntity = realPlayerHeroEntityMap.get(key);
            if (Objects.isNull(dbPlayerHeroEntity)) {
                playerEntity.addHero(playerHeroEntity);
                continue;
            }

            dbPlayerHeroEntity.setLevelInfo(playerHeroEntity.getLevelInfo());
        }
    }

    private Map<String, PlayerSpellEntity> playerSpellEntityToMap(List<PlayerSpellEntity> playerSpellEntities) {
        return playerSpellEntities.stream()
                                  .collect(Collectors.toMap((playerSpellEntity -> playerSpellEntity.getId()
                                                                                                   .getName()), playerSpellEntity -> playerSpellEntity));

    }

    private Map<String, PlayerTroopsEntity> playerTroopEntitiesToMap(List<PlayerTroopsEntity> playerTroopsEntities) {
        return playerTroopsEntities.stream()
                                 .collect(Collectors.toMap((playerTroopsEntity -> playerTroopsEntity.getId()
                                                                                                    .getName()), playerTroopsEntity -> playerTroopsEntity));

    }

    private Map<String, PlayerHeroEquipmentEntity> playerHeroEquipmentEntitiesToMap(List<PlayerHeroEquipmentEntity> playerHeroEquipmentEntities) {
        return playerHeroEquipmentEntities.stream()
                                          .collect(Collectors.toMap((playerHeroEquipmentEntity -> playerHeroEquipmentEntity.getId()
                                                                                                                           .getName()), playerHeroEquipmentEntity -> playerHeroEquipmentEntity));

    }

    private Map<String, PlayerHeroEntity> playerHeroEntitiesToMap(List<PlayerHeroEntity> playerHeroEntities) {
        return playerHeroEntities.stream()
                                 .collect(Collectors.toMap((playerHeroEntity -> playerHeroEntity.getId()
                                                                                                .getName()), playerHeroEntity -> playerHeroEntity));

    }

    private void modifyPlayer(PlayerEntity playerEntity, Player player) {
        playerEntity.setName(player.getName());
        playerEntity.setExpLevel(player.getExpLevel());
        playerEntity.setTownHallLevel(player.getTownHallLevel());
        playerEntity.setTrophies(player.getTrophies());
        playerEntity.setBestTrophies(player.getBestTrophies());
        playerEntity.setDonations(player.getDonations());
        playerEntity.setDonationsReceived(player.getDonationsReceived());
        playerEntity.setWarStars(player.getWarStars());
        playerEntity.setAttackWins(player.getAttackWins());
        playerEntity.setDefenseWins(player.getDefenseWins());

        playerEntity.setRole(player.getRole());
        playerEntity.setWarPreference(WarPreferenceType.out);
        if (Objects.nonNull(player.getWarPreference())) {
            playerEntity.setWarPreference(WarPreferenceType.valueOf(player.getWarPreference()));
        }
    }

    @Transactional
    public void deletePlayer(String playerTag) {
        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isEmpty()) {
            return;
        }

        PlayerEntity player = findPlayer.get();
        playerRepository.delete(player);

        deleteClanAssigned(player);
        deleteClanLeague(player);
    }

    private void deleteClanAssigned(PlayerEntity player) {
        String latestAssignedSeasonDate = clanAssignedPlayerRepository.findLatestSeasonDate();
        if (Objects.isNull(latestAssignedSeasonDate)) {
            return;
        }

        clanAssignedPlayerRepository.deleteById(ClanAssignedPlayerPKEntity.builder()
                                                                          .seasonDate(latestAssignedSeasonDate)
                                                                          .playerTag(player.getPlayerTag())
                                                                          .build());
    }

    private void deleteClanLeague(PlayerEntity player) {
        String latestAssignedSeasonDate = clanLeagueAssignedPlayerRepository.findLatestLeagueSeasonDate();
        if (Objects.isNull(latestAssignedSeasonDate)) {
            return;
        }

        clanLeagueAssignedPlayerRepository.deleteById(ClanAssignedPlayerPKEntity.builder()
                                                                                .seasonDate(latestAssignedSeasonDate)
                                                                                .playerTag(player.getPlayerTag())
                                                                                .build());
    }

    public List<String> findAllPlayerTags() {
        return playerRepository.findAllPlayerTag();
    }

    @Transactional
    public void changePlayerSupport(PlayerModify playerModify) {

        PlayerEntity player = playerRepository.findById(playerModify.getPlayerTag())
                                              .orElseGet(() -> createPlayerEntity(playerModify.getPlayerTag()));

        player.changeSupportYn(playerModify.getSupportYn());
        playerRepository.save(player);

        // 지원계정 전환 시 배정된 클랜 제거
        if (playerModify.isSupport()) {
            clanAssignedPlayerRepository.deleteById(ClanAssignedPlayerPKEntity.builder()
                                                                              .seasonDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")))
                                                                              .playerTag(player.getPlayerTag())
                                                                              .build());
        }
    }

    public List<PlayerEntity> findAllPlayersBy(List<String> playerTags) {
        return playerRepository.findAllById(playerTags);
    }

    public List<RankingHeroEquipmentResponse> getRankingHeroEquipments(String clanTag) {
        List<String> playerTags = getClanAssignedPlayerTags(clanTag);

        return playerRepository.selectRankingHeroEquipments(playerTags)
                               .stream()
                               .map(rankingHeroEquipmentResponseConverter::convert)
                               .collect(Collectors.toList());
    }

    private List<String> getClanAssignedPlayerTags(String clanTag) {
        if ("all".equals(clanTag)) {
            // 전체 처리
            return playerRepository.findAllPlayerTag();
        }

        String latestSeasonDate = clanAssignedPlayerRepository.findLatestSeasonDate();
        List<ClanAssignedPlayerEntity> clanAssignedPlayerEntities = clanAssignedPlayerRepository.findClanAssignedPlayersByClanTagAndSeasonDate(clanTag, latestSeasonDate);
        return clanAssignedPlayerEntities.stream()
                                         .map(ClanAssignedPlayerEntity::getPlayerTag)
                                         .toList();
    }

    public List<RankingHallOfFame> getRankingTrophiesCurrent() {
        return playerRepository.selectRankingTrophiesCurrent(PageRequest.of(0, hallOfFameConfig.getRanking()));
    }

    public List<RankingHallOfFame> getRankingAttackWins() {
        return playerRepository.selectRankingAttackWins(PageRequest.of(0, hallOfFameConfig.getRanking()));
    }

    @Transactional
    public void registerSupportPlayerBulk(List<String> supportPlayerTags) {
        if (CollectionUtils.isEmpty(supportPlayerTags)) return;

        // step1. 기존 지원계정 초기화
        long clearSupportPlayerCount = playerQueryRepository.resetSupportPlayers();
        log.info("clear support player count: {}", clearSupportPlayerCount);

        // step2. 지원계정 설정
        long updatedSupportPlayerCount = playerQueryRepository.updateSupportPlayers(supportPlayerTags);

        // step3. 지원계정 전환 시 배정된 클랜 제거
        long exceptPlayerCount = processExceptAssignedClan(supportPlayerTags);
        log.info("except assigned clan player count: {}", exceptPlayerCount);

        // step4. 요청한 계정 재설정이 완료된 경우
        if (updatedSupportPlayerCount == supportPlayerTags.size()) return;

        // step5. 그렇지 않은 경우 신규 계정 등록 및 지원계정 설정
        List<String> missingPlayerTags = playerQueryRepository.findAllNotExistsByPlayerTags(supportPlayerTags);

        for (String playerTag : missingPlayerTags) {
            try {
                PlayerEntity playerEntity = createPlayerEntity(playerTag);
                playerEntity.changeSupportYn(YnType.Y); //지원계정 설정

                playerQueryRepository.save(playerEntity);
            } catch (Exception e) {
                log.error("[not registered player] tag: {}, reason: {}", playerTag, e.getMessage());
            }
        }
    }

    private long processExceptAssignedClan(List<String> playerTags) {
        // 현재 배정월 기준 배정클랜 제거
        String seasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return clanAssignedPlayerQueryRepository.deleteBySeasonDateAndPlayerTags(seasonDate, playerTags);
    }
}
