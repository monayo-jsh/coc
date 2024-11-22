package open.api.coc.clans.service;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.common.exception.handler.ExceptionHandler;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerDTO;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPK;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanLeagueAssignedPlayerEntity;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.repository.clan.ClanAssignedPlayerQueryRepository;
import open.api.coc.clans.database.repository.clan.ClanAssignedPlayerRepository;
import open.api.coc.clans.database.repository.clan.ClanContentRepository;
import open.api.coc.clans.database.repository.clan.ClanLeagueAssignedPlayerRepository;
import open.api.coc.clans.database.repository.clan.ClanLeagueWarRepository;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.player.PlayerRepository;
import open.api.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import open.api.coc.clans.domain.clans.ClanAssignedPlayer;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulk;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueGroupResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarResponse;
import open.api.coc.clans.domain.clans.converter.ClanCurrentWarLeagueGroupResponseConverter;
import open.api.coc.clans.domain.clans.converter.ClanCurrentWarResConverter;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.domain.players.converter.PlayerResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClanApiService clanApiService;
    private final ClanWarService clanWarService;

    private final ClanRepository clanRepository;
    private final ClanContentRepository clanContentRepository;

    private final ClanAssignedPlayerRepository clanAssignedPlayerRepository;
    private final ClanAssignedPlayerQueryRepository clanAssignedPlayerQueryRepository;
    private final ClanLeagueAssignedPlayerRepository clanLeagueAssignedPlayerRepository;

    private final ClanLeagueWarRepository clanLeagueWarRepository;

    private final ClanCurrentWarResConverter clanCurrentWarResConverter;

    private final PlayerRepository playerRepository;
    private final PlayersService playersService;
    private final PlayerResponseConverter playerResponseConverter;

    private final ClanCurrentWarLeagueGroupResponseConverter clanCurrentWarLeagueGroupResponseConverter;

    public ClanCurrentWarResponse getClanCurrentWar(String clanTag) {
        ClanWar clanCurrentWar = clanApiService.findClanCurrentWarByClanTag(clanTag)
                                               .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "현재 클랜 전쟁 조회 실패"));

        if (clanCurrentWar.isNotNotInWar()) {
            ClanWarType clanWarType = getClanWarType(clanTag);
            clanWarService.writeClanWarToFile(clanCurrentWar);
            ClanWarEntity clanWarEntity = clanWarService.mergeClanWar(clanCurrentWar, clanWarType);
            clanWarService.mergeClanWarMember(clanWarEntity, clanCurrentWar);
        }

        return clanCurrentWarResConverter.convert(clanCurrentWar);
    }

    private ClanWarType getClanWarType(String clanTag) {
        Optional<ClanContentEntity> findClanContent = clanContentRepository.findById(clanTag);
        if (findClanContent.isEmpty()) {
            //설정값이 없으면 일반 클랜전 처리
            return ClanWarType.NONE;
        }

        ClanContentEntity clanContent = findClanContent.get();
        if (clanContent.isClanWarParallel()) {
            //병행 클랜전 설정된 클랜의 경우 병행 클랜전 처리
            return ClanWarType.PARALLEL;
        }

        // 그 외는 일반 클랜전 처리
        // ~ 리그전은 별도 메서드에서 처리
        return ClanWarType.NONE;
    }


    public ClanCurrentWarResponse getLeagueWar(String clanTag, String roundTag) {
        ClanWar leagueWar = clanApiService.findLeagueWarByRoundTag(roundTag)
                                          .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "리그전 조회 실패"));

        leagueWar.swapWarClan(clanTag);
        return clanCurrentWarResConverter.convert(leagueWar);
    }

    public ClanAssignedMemberListResponse findClanAssignedMembers(String clanTag) {
        String latestSeasonDate = clanAssignedPlayerQueryRepository.findLatestSeasonDate();

        List<ClanAssignedPlayerEntity> clanAssignedPlayers = clanAssignedPlayerRepository.findClanAssignedPlayersByClanTagAndSeasonDate(clanTag, latestSeasonDate);

        List<String> playerTags = clanAssignedPlayers.stream()
                                                      .map(ClanAssignedPlayerEntity::getPlayerTag)
                                                      .toList();
        List<PlayerResponse> players = playersService.findPlayerBy(playerTags);

        return ClanAssignedMemberListResponse.create(clanTag, latestSeasonDate, players);
    }

    @Transactional
    public void postClanAssignedMember(String clanTag, String seasonDate, String playerTag) {

        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isEmpty()) {
            // 배정 시 등록되지 않은 클랜원은 등록 처리
            playersService.registerPlayer(playerTag);
        }

        ClanAssignedPlayerPK clanAssignedPlayerPK = ClanAssignedPlayerPK.builder()
                                                                        .seasonDate(seasonDate)
                                                                        .playerTag(playerTag)
                                                                        .build();

        Optional<ClanAssignedPlayerEntity> findClanAssignedPlayer = clanAssignedPlayerRepository.findById(clanAssignedPlayerPK);
        if (findClanAssignedPlayer.isPresent()) {
            ClanAssignedPlayerEntity clanAssignedPlayerEntity = findClanAssignedPlayer.get();
            if (Objects.equals(clanTag, clanAssignedPlayerEntity.getClan().getTag())) {
                // 이미 배정
                return;
            }

            ClanEntity clanEntity = clanRepository.findById(clanAssignedPlayerEntity.getClan().getTag())
                                                  .orElseThrow(() -> ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA,
                                                                                                                clanAssignedPlayerEntity.getClan().getName() + "에 배정된 상태"));

            throw ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA.getCode(), "[%s] 배정된 상태".formatted(clanEntity.getName()));
        }

        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(clanTag)));
        ClanAssignedPlayerEntity clanAssignedPlayer = ClanAssignedPlayerEntity.builder()
                                                                              .id(clanAssignedPlayerPK)
                                                                              .clan(clan)
                                                                              .build();

        clanAssignedPlayerRepository.save(clanAssignedPlayer);
    }

    @Transactional
    public void deleteClanAssignedMember(String clanTag, String seasonDate, String playerTag) {
        ClanAssignedPlayerPK clanAssignedPlayerPK = ClanAssignedPlayerPK.builder()
                                                                        .seasonDate(seasonDate)
                                                                        .playerTag(playerTag)
                                                                        .build();

        clanAssignedPlayerRepository.deleteById(clanAssignedPlayerPK);
    }

    public ClanAssignedMemberListResponse getLatestClanAssignedMembers() {

        String latestSeasonDate = clanAssignedPlayerQueryRepository.findLatestSeasonDate();

        List<ClanAssignedPlayerDTO> clanAssignedPlayers = clanAssignedPlayerQueryRepository.findAllBySeasonDate(latestSeasonDate);

        List<PlayerResponse> players = clanAssignedPlayers.stream()
                                                          .map(playerResponseConverter::convert)
                                                          .collect(Collectors.toList());

        return ClanAssignedMemberListResponse.create(Strings.EMPTY, latestSeasonDate, players);
    }

    @Transactional
    public void registerClanAssignedMembers(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        // 배정 클랜원 클랜원 데이터 자동 입력 처리
        processRegistrationBatchUnRegisteredPlayers(clanAssignedPlayerBulk);
        // 지원계정 해제 처리
        excludeSupportPlayers(clanAssignedPlayerBulk);

        List<ClanAssignedPlayerEntity> clanAssignedPlayers = makeClanAssignedPlayerEntities(clanAssignedPlayerBulk);

        // 배정 일괄 삭제
        clanAssignedPlayerRepository.deleteAllBySeasonDate(clanAssignedPlayerBulk.getSeasonDate());

        // 배정 일괄 등록
        clanAssignedPlayerRepository.saveAll(clanAssignedPlayers);
    }

    public void processRegistrationBatchUnRegisteredPlayers(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        // 자동 등록
        List<String> playerTags = clanAssignedPlayerBulk.getPlayers()
                                                        .stream()
                                                        .map(ClanAssignedPlayer::getPlayerTag)
                                                        .toList();
        registerPlayers(playerTags);
    }

    private void excludeSupportPlayers(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        List<String> playerTags = clanAssignedPlayerBulk.getPlayers()
                                                        .stream()
                                                        .map(ClanAssignedPlayer::getPlayerTag)
                                                        .toList();

        List<PlayerEntity> playerEntities = playersService.findAllPlayersBy(playerTags);
        for(PlayerEntity player : playerEntities) {
            // 지원계정 해제
            player.setSupportYn(YnType.N);
        }
    }

    private List<ClanAssignedPlayerEntity> makeClanAssignedPlayerEntities(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        return clanAssignedPlayerBulk.getPlayers()
                                     .stream()
                                     .map(player -> makeClanAssignedPlayerEntity(clanAssignedPlayerBulk.getSeasonDate(), player))
                                     .collect(Collectors.toList());
    }

    private ClanAssignedPlayerEntity makeClanAssignedPlayerEntity(String seasonDate, ClanAssignedPlayer player) {
        ClanEntity clan = clanRepository.findById(player.getClanTag())
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(player.getClanTag())));

        return ClanAssignedPlayerEntity.builder()
                                       .id(ClanAssignedPlayerPK.builder()
                                                               .seasonDate(seasonDate)
                                                               .playerTag(player.getPlayerTag())
                                                               .build())
                                       .clan(clan)
                                       .build();
    }

    public ClanAssignedMemberListResponse getLatestLeagueAssignedMembers() {

        String latestSeasonDate = clanLeagueAssignedPlayerRepository.findLatestLeagueSeasonDate();
        if (ObjectUtils.isEmpty(latestSeasonDate)) {
            latestSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanAssignedPlayerDTO> clanAssignedPlayers = clanLeagueAssignedPlayerRepository.findBySeasonDate(latestSeasonDate);

        List<PlayerResponse> players = clanAssignedPlayers.stream()
                                                          .map(playerResponseConverter::convert)
                                                          .collect(Collectors.toList());

        return ClanAssignedMemberListResponse.create(Strings.EMPTY, latestSeasonDate, players);
    }

    public ClanAssignedMemberListResponse findClanLeagueAssignedMembers(String clanTag) {
        String latestLeagueSeasonDate = clanLeagueAssignedPlayerRepository.findLatestLeagueSeasonDate();
        if (ObjectUtils.isEmpty(latestLeagueSeasonDate)) {
            latestLeagueSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanLeagueAssignedPlayerEntity> clanLeagueAssignedPlayers = clanLeagueAssignedPlayerRepository.findClanLeagueAssignedPlayersByClanTagAndSeasonDate(clanTag, latestLeagueSeasonDate);

        List<String> playerTags = clanLeagueAssignedPlayers.stream()
                                                           .map(ClanLeagueAssignedPlayerEntity::getPlayerTag)
                                                           .toList();
        List<PlayerResponse> players = playersService.findPlayerBy(playerTags);

        return ClanAssignedMemberListResponse.create(clanTag, latestLeagueSeasonDate, players);
    }

    @Transactional
    public void postLeagueAssignedMember(String clanTag, String seasonDate, String playerTag) {

        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isEmpty()) {
            // 배정 시 등록되지 않은 클랜원은 등록 처리
            playersService.registerPlayer(playerTag);
        }

        ClanAssignedPlayerPK clanAssignedPlayerPK = ClanAssignedPlayerPK.builder()
                                                                        .seasonDate(seasonDate)
                                                                        .playerTag(playerTag)
                                                                        .build();

        Optional<ClanLeagueAssignedPlayerEntity> findClanLeagueAssignedPlayer = clanLeagueAssignedPlayerRepository.findById(clanAssignedPlayerPK);
        if (findClanLeagueAssignedPlayer.isPresent()) {
            ClanLeagueAssignedPlayerEntity clanLeagueAssignedPlayer = findClanLeagueAssignedPlayer.get();
            if (Objects.equals(clanTag, clanLeagueAssignedPlayer.getClan().getTag())) {
                // 이미 배정
                return;
            }

            ClanEntity clanEntity = clanRepository.findById(clanLeagueAssignedPlayer.getClan().getTag())
                                                  .orElseThrow(() -> ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA,
                                                                                                                clanLeagueAssignedPlayer.getClan().getName() + "에 배정된 상태"));

            throw ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA.getCode(), "[%s] 리그 배정된 상태".formatted(clanEntity.getName()));
        }

        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(clanTag)));
        ClanLeagueAssignedPlayerEntity clanLeagueAssignedPlayer = ClanLeagueAssignedPlayerEntity.builder()
                                                                                    .id(clanAssignedPlayerPK)
                                                                                    .clan(clan)
                                                                                    .build();

        clanLeagueAssignedPlayerRepository.save(clanLeagueAssignedPlayer);
    }

    @Transactional
    public void deleteClanLeagueAssignedMember(String clanTag, String seasonDate, String playerTag) {
        ClanAssignedPlayerPK clanAssignedPlayerPK = ClanAssignedPlayerPK.builder()
                                                                        .seasonDate(seasonDate)
                                                                        .playerTag(playerTag)
                                                                        .build();

        clanLeagueAssignedPlayerRepository.deleteById(clanAssignedPlayerPK);
    }

    @Transactional
    public void registerClanLeagueAssignedMembers(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        // 배정 클랜원 클랜원 데이터 자동 입력 처리
        processRegistrationBatchUnRegisteredPlayers(clanAssignedPlayerBulk);

        List<ClanLeagueAssignedPlayerEntity> clanAssignedPlayers = makeClanLeagueAssignedPlayerEntities(clanAssignedPlayerBulk);

        // 배정 일괄 삭제
        clanLeagueAssignedPlayerRepository.deleteAllBySeasonDate(clanAssignedPlayerBulk.getSeasonDate());

        // 배정 일괄 등록
        clanLeagueAssignedPlayerRepository.saveAll(clanAssignedPlayers);
    }

    private List<ClanLeagueAssignedPlayerEntity> makeClanLeagueAssignedPlayerEntities(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        return clanAssignedPlayerBulk.getPlayers()
                                     .stream()
                                     .map(player -> makeClanLeagueAssignedPlayerEntity(clanAssignedPlayerBulk.getSeasonDate(), player))
                                     .collect(Collectors.toList());
    }

    private ClanLeagueAssignedPlayerEntity makeClanLeagueAssignedPlayerEntity(String seasonDate, ClanAssignedPlayer player) {
        ClanEntity clan = clanRepository.findById(player.getClanTag())
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(player.getClanTag())));

        return ClanLeagueAssignedPlayerEntity.builder()
                                             .id(ClanAssignedPlayerPK.builder()
                                                                     .seasonDate(seasonDate)
                                                                     .playerTag(player.getPlayerTag())
                                                                     .build())
                                             .clan(clan)
                                             .build();
    }

    @Transactional
    public ClanCurrentWarLeagueGroupResponse getClanCurrentWarLeagueGroup(String clanTag) {
        String season = getCurrentSeason();

        createClanLeagueWar(clanTag, season);

        ClanCurrentWarLeagueGroup clanCurrentWarLeagueGroup = clanWarService.findClanCurrentWarLeagueGroupFromFile(season, clanTag);

        if (ObjectUtils.isEmpty(clanCurrentWarLeagueGroup) || clanCurrentWarLeagueGroup.isWarNotEnded()) {
            //파일이 없거나 전쟁이 끝나지 않으면 연동 및 파일 생성 & 갱신
            clanCurrentWarLeagueGroup = clanApiService.findClanCurrentWarLeagueGroupBy(clanTag)
                                                      .orElseThrow(() -> createNotFoundException("클랜(%s) 현재 리그전 정보 조회 실패".formatted(clanTag)));

            if (clanCurrentWarLeagueGroup.isNotInWar()) {
                clanCurrentWarLeagueGroup.setSeason(season);
            }

            clanWarService.writeClanWarLeagueSeasonGroup(clanTag, clanCurrentWarLeagueGroup);
        }

        return clanCurrentWarLeagueGroupResponseConverter.convert(clanCurrentWarLeagueGroup);
    }

    private String getCurrentSeason() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createClanLeagueWar(String clanTag, String season) {

        ClanEntity clanEntity = clanRepository.findById(clanTag).orElseGet(null);
        if (Objects.isNull(clanEntity)) return; // 클랜 메타 정보가 없는 경우

        Optional<ClanLeagueWarEntity> findClanLeagueWar = clanLeagueWarRepository.findByClanTagAndSeason(clanTag, season);

        // 생성된 경우
        if (findClanLeagueWar.isPresent()) {
            ClanLeagueWarEntity clanLeagueWar = findClanLeagueWar.get();
            if (clanLeagueWar.isUnranked()) {
                clanLeagueWarRepository.update(clanLeagueWar.getLeagueWarId(), clanEntity.getWarLeague());
            }
            return;
        }

        // 현재 소속된 리그 정보 저장
        ClanLeagueWarEntity clanLeagueWarEntity = ClanLeagueWarEntity.builder()
                                                                     .clanTag(clanTag)
                                                                     .season(season)
                                                                     .warLeague(clanEntity.getWarLeague())
                                                                     .build();

        clanLeagueWarRepository.save(clanLeagueWarEntity);
    }

    public ClanCurrentWarResponse getClanWarLeagueRound(String clanTag, String season, String warTag) {
        ClanWar clanWar = clanWarService.findClanWarLeagueFromFile(clanTag, season, warTag);

        if (ObjectUtils.isEmpty(clanWar)) {
            clanWar = clanApiService.findWarLeagueByWarTag(warTag)
                                    .orElseThrow(() -> createNotFoundException("리그전 전쟁 정보 조회 실패 (%s)".formatted(warTag)));

            if (clanWar.isWarEnded()) {
                clanWarService.writeClanWarLeagueResult(clanTag, season, warTag, clanWar);
            }
        }

        // 아카데미 클랜 리그라운드 정보인경우 데이터 수집
        if (clanWar.isContainClanTag(clanTag)) {
            clanWarService.mergeClanWarLeague(clanTag, warTag, clanWar);
        }

        return clanCurrentWarResConverter.convert(clanWar);
    }

    public void registerPlayers(List<String> requestPlayerTags) {
        List<String> playerTags = playersService.findAllPlayerTags();
        Set<String> allPlayerTagSet = new HashSet<>(playerTags);

        for (String playerTag : requestPlayerTags) {
            if (allPlayerTagSet.contains(playerTag)) continue;

            try {
                playersService.registerPlayer(playerTag);
            } catch (Exception e) {
                log.error("클랜원 자동 등록 실패 : {}", e.getMessage());
            }
        }
    }

    public String getLatestLeagueAssignedDate() {
        return clanLeagueAssignedPlayerRepository.findLatestLeagueSeasonDate();
    }
}
