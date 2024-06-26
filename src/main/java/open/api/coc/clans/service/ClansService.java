package open.api.coc.clans.service;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.AcademeClan;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.common.exception.handler.ExceptionHandler;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPKEntity;
import open.api.coc.clans.database.entity.clan.ClanBadgeEntity;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanLeagueAssignedPlayerEntity;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.common.converter.IconUrlEntityConverter;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.repository.clan.ClanAssignedPlayerRepository;
import open.api.coc.clans.database.repository.clan.ClanContentRepository;
import open.api.coc.clans.database.repository.clan.ClanLeagueAssignedPlayerRepository;
import open.api.coc.clans.database.repository.clan.ClanRepository;
import open.api.coc.clans.database.repository.player.PlayerRepository;
import open.api.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import open.api.coc.clans.domain.clans.ClanAssignedPlayer;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulk;
import open.api.coc.clans.domain.clans.ClanContent;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueGroupResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarResponse;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanRequest;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.LeagueClanRes;
import open.api.coc.clans.domain.clans.converter.ClanCurrentWarLeagueGroupResponseConverter;
import open.api.coc.clans.domain.clans.converter.ClanCurrentWarResConverter;
import open.api.coc.clans.domain.clans.converter.ClanMemberListResConverter;
import open.api.coc.clans.domain.clans.converter.ClanResponseConverter;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.domain.players.converter.PlayerResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.clan.Clan;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanMemberList;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClanApiService clanApiService;

    private final ClanRepository clanRepository;
    private final ClanContentRepository clanContentRepository;
    private final ClanAssignedPlayerRepository clanAssignedPlayerRepository;
    private final ClanLeagueAssignedPlayerRepository clanLeagueAssignedPlayerRepository;

    private final ClanResponseConverter clanResponseConverter;
    private final IconUrlEntityConverter iconUrlEntityConverter;
    private final ClanCurrentWarResConverter clanCurrentWarResConverter;
    private final ClanMemberListResConverter clanMemberListResConverter;

    private final PlayerRepository playerRepository;
    private final PlayersService playersService;
    private final PlayerResponseConverter playerResponseConverter;

    private final TimeConverter timeConverter;

    private final ClanCurrentWarLeagueGroupResponseConverter clanCurrentWarLeagueGroupResponseConverter;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public ClanResponse findClanByClanTag(String clanTag) {
        Clan clan = clanApiService.findClanByClanTag(clanTag)
                                  .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 조회 실패"));

        return clanResponseConverter.convert(clan);
    }

    public ClanCurrentWarResponse getClanCurrentWar(String clanTag) {
        ClanWar clanCurrentWar = clanApiService.findClanCurrentWarByClanTag(clanTag)
                                               .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "현재 클랜 전쟁 조회 실패"));

        if (clanCurrentWar.isNotNotInWar()) {
            writeClanWarResult(clanTag, clanCurrentWar);
        }

        return clanCurrentWarResConverter.convert(clanCurrentWar);
    }


    public ClanCurrentWarResponse getLeagueWar(String clanTag, String roundTag) {
        ClanWar leagueWar = clanApiService.findLeagueWarByRoundTag(roundTag)
                                          .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "리그전 조회 실패"));

        leagueWar.swapWarClan(clanTag);
        return clanCurrentWarResConverter.convert(leagueWar);
    }

    public List<ClanResponse> getClanList() {
        List<ClanEntity> clans = clanRepository.findAll();

        return clans.stream()
                    .map(clanResponseConverter::convert)
                    .sorted(Comparator.comparing(ClanResponse::getOrder))
                    .collect(Collectors.toList());
    }

    public List<ClanResponse> getWarClanResList() {
        List<ClanEntity> clanWarList = clanRepository.findWarClanList();

        return clanWarList.stream()
                          .map(clanResponseConverter::convert)
                          .collect(Collectors.toList());
    }

    public List<ClanResponse> getWarParallelClanResList() {
        List<ClanEntity> clanWarParallelList = clanRepository.findClanWarParallelList();

        return clanWarParallelList.stream()
                                  .map(clanResponseConverter::convert)
                                  .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanCaptialList() {
        List<ClanEntity> clanCapitalList = clanRepository.findCapitalClanList();

        return clanCapitalList.stream()
                              .map(clanResponseConverter::convert)
                              .collect(Collectors.toList());
    }


    public LeagueClanRes getLeagueClan(String clanTag) throws IOException {
        AcademeClan clan = AcademeClan.findByTag(clanTag);
        return LeagueClanRes.create(clan);
    }


    public ClanMemberListRes findClanMembersByClanTag(String clanTag) {
        ClanMemberList clanMemberList = clanApiService.findClanMembersByClanTag(clanTag)
                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 사용자 조회 실패"));

        ClanMemberListRes clanMemberListRes = clanMemberListResConverter.convert(clanMemberList);
        clanMemberListRes.setClanTag(clanTag);
        return clanMemberListRes;
    }

    public List<ClanMemberListRes> findClanMembersByClanTags(List<String> clanTags) {
        return clanTags.stream()
                       .parallel()
                       .map(this::findClanMembersByClanTag)
                       .collect(Collectors.toList());
    }

    @Transactional
    public List<ClanResponse> findClanByClanTags(List<String> clanTags) {
        return clanTags.stream()
                       .map(clanApiService::findClanByClanTag)
                       .filter(Optional::isPresent)
                       .map(findClan -> {
                           Clan clan =findClan.get();
                           mergeClan(clan);
                           return clanResponseConverter.convert(clan);
                       })
                       .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mergeClan(Clan clan) {
        Optional<ClanEntity> findClan = clanRepository.findById(clan.getTag());
        findClan.ifPresent(clanEntity -> clanEntity.setWarLeague(clan.getWarLeagueName()));
    }

    @Transactional
    public void updateClanContentStatus(ClanContent request) {

        ClanContentEntity clanContent = clanContentRepository.findById(request.getTag())
                                                             .orElseThrow(() -> createNotFoundException("클랜[%s] 컨텐츠 정보".formatted(request.getTag())));

        if (StringUtils.hasText(request.getClanWarYn())) {
            clanContent.setClanWarYn(request.getClanWarYn());
        }

        if (StringUtils.hasText(request.getClanWarLeagueYn())) {
            clanContent.setWarLeagueYn(request.getClanWarLeagueYn());
        }

        if (StringUtils.hasText(request.getClanCapitalYn())) {
            clanContent.setClanCapitalYn(request.getClanCapitalYn());
        }

        if (StringUtils.hasText(request.getClanWarParallelYn())) {
            clanContent.setClanWarParallelYn(request.getClanWarParallelYn());
        }

        clanContentRepository.save(clanContent);
    }

    @Transactional
    public void deleteClan(String clanTag) {
        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElse(null);

        if (ObjectUtils.isEmpty(clan)) {
            // 클랜 없는 경우 성공
            return;
        }

        clan.setVisibleYn(YnType.N);
        clanRepository.save(clan);
    }

    @Transactional
    public ClanResponse registerClan(ClanRequest clanRequest) {
        clanRequest.validate();

        Clan clan = clanApiService.findClanByClanTag(clanRequest.getTag())
                                  .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 정보 없음".formatted(clanRequest.getTag())));

        Optional<ClanEntity> clanEntity = clanRepository.findById(clanRequest.getTag());
        if (clanEntity.isPresent()) {
            ClanEntity updateClanEntity = clanEntity.get();
            updateClanEntity.setWarLeague(clan.getWarLeagueName());
            updateClanEntity.setVisibleYn(YnType.Y);
            ClanEntity resultClan = clanRepository.save(updateClanEntity);
            return clanResponseConverter.convert(resultClan);
        }

        Integer clanMaxOrders = clanRepository.selectMaxOrders();
        if (Objects.isNull(clanMaxOrders)) {
            clanMaxOrders = 0;
        }

        ClanEntity createClan = createClanEntity(clan, clanMaxOrders);
        createClan.changeClanContent(ClanContentEntity.empty(clanRequest.getTag()));
        createClan.changeBadgeUrl(ClanBadgeEntity.builder()
                                                 .tag(clanRequest.getTag())
                                                 .iconUrl(iconUrlEntityConverter.convert(clanRequest.getBadgeUrl()))
                                                 .build());
        clanRepository.save(createClan);

        return clanResponseConverter.convert(createClan);
    }

    private ClanEntity createClanEntity(Clan clan, Integer clanMaxOrders) {
        return ClanEntity.builder()
                         .tag(clan.getTag())
                         .name(clan.getName())
                         .warLeague(clan.getWarLeagueName())
                         .order(clanMaxOrders + 1)
                         .visibleYn(YnType.Y)
                         .regDate(LocalDateTime.now())
                         .build();
    }

    public ClanAssignedMemberListResponse findClanAssignedMembers(String clanTag) {
        String latestSeasonDate = clanAssignedPlayerRepository.findLatestSeasonDate();
        if (ObjectUtils.isEmpty(latestSeasonDate)) {
            latestSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

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

        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
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
        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
                                                                                    .seasonDate(seasonDate)
                                                                                    .playerTag(playerTag)
                                                                                    .build();

        clanAssignedPlayerRepository.deleteById(clanAssignedPlayerPK);
    }

    public ClanAssignedMemberListResponse getLatestClanAssignedMembers() {

        String latestSeasonDate = clanAssignedPlayerRepository.findLatestSeasonDate();
        if (ObjectUtils.isEmpty(latestSeasonDate)) {
            latestSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanAssignedPlayerEntity> clanAssignedPlayers = clanAssignedPlayerRepository.findBySeasonDate(latestSeasonDate);

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
                                       .id(ClanAssignedPlayerPKEntity.builder()
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

        List<ClanLeagueAssignedPlayerEntity> clanAssignedPlayers = clanLeagueAssignedPlayerRepository.findBySeasonDate(latestSeasonDate);

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

        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
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
        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
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
                                             .id(ClanAssignedPlayerPKEntity.builder()
                                                                           .seasonDate(seasonDate)
                                                                           .playerTag(player.getPlayerTag())
                                                                           .build())
                                             .clan(clan)
                                             .build();
    }

    public Optional<ClanEntity> findClanEntityBy(String clanTag) {
        return clanRepository.findById(clanTag);
    }

    public List<ClanResponse> getWarLeagueClanResList() {
        List<ClanEntity> clanWarLeagueList = clanRepository.findWarLeagueClanList();

        return clanWarLeagueList.stream()
                                .map(clanResponseConverter::convert)
                                .collect(Collectors.toList());
    }

    public ClanCurrentWarLeagueGroupResponse getClanCurrentWarLeagueGroup(String clanTag) {
        String season = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        ClanCurrentWarLeagueGroup clanCurrentWarLeagueGroup = findClanCurrentWarLeagueGroupFromFile(season, clanTag);

        if (ObjectUtils.isEmpty(clanCurrentWarLeagueGroup) || clanCurrentWarLeagueGroup.isWarNotEnded()) {
            //파일이 없거나 전쟁이 끝나지 않으면 연동 및 파일 생성 & 갱신
            clanCurrentWarLeagueGroup = clanApiService.findClanCurrentWarLeagueGroupBy(clanTag)
                                                      .orElseThrow(() -> createNotFoundException("클랜(%s) 현재 리그전 정보 조회 실패".formatted(clanTag)));

            writeClanWarLeagueSeasonGroup(clanTag, clanCurrentWarLeagueGroup);
        }

        return clanCurrentWarLeagueGroupResponseConverter.convert(clanCurrentWarLeagueGroup);
    }

    private ClanCurrentWarLeagueGroup findClanCurrentWarLeagueGroupFromFile(String season, String clanTag) {
        final String path = LEAGUE_WAR_INFO_DIR.replace("{season}", season)
                                               .replace("{clanTag}", clanTag);

        // directory: ./war-league/{season}/{clanTag}/round/{warTag}.json
        File file = new File(path, getLeagueWarSeasonInfoFileName());

        // Not Found.
        if (!file.exists()) return null;

        try {
            return objectMapper.readValue(file, ClanCurrentWarLeagueGroup.class);
        } catch (Exception e) {
            log.error("파일({}) 파싱 오류, {}", file.getAbsoluteFile(), e.getMessage(), e);
            return null;
        }
    }

    public ClanCurrentWarResponse getClanWarLeagueRound(String clanTag, String season, String warTag) {
        ClanWar clanWar = findClanWarLeagueFromFile(clanTag, season, warTag);

        if (ObjectUtils.isEmpty(clanWar)) {
            clanWar = clanApiService.findWarLeagueByWarTag(warTag)
                                    .orElseThrow(() -> createNotFoundException("리그전 전쟁 정보 조회 실패 (%s)".formatted(warTag)));

            if (clanWar.isWarEnded()) {
                writeClanWarLeagueResult(clanTag, season, warTag, clanWar);
            }
        }

        return clanCurrentWarResConverter.convert(clanWar);
    }

    final String CLAN_WAR_ROOT_DIR = "./clan-war";
    final String CLAN_WAR_GROUP_DIR = CLAN_WAR_ROOT_DIR + "/{clanTag}";
    private void writeClanWarResult(String clanTag, ClanWar clanWar) {
        try {
            // directory: ./clan-war/{clanTag}
            final String path = CLAN_WAR_GROUP_DIR.replace("{clanTag}", clanTag);

            ClassPathResource resource = checkAndMakeDirectory(path);

            long startTimeMilliSec = timeConverter.toEpochMilliSecond(clanWar.getStartTime());
            LocalDate startDate = timeConverter.toLocalDate(startTimeMilliSec);
            String fileName = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);

            File file = new File(resource.getPath(), makeJsonFileName(fileName));
            makeEmptyFile(file);

            writeFile(file, clanWar);
        } catch (IOException e) {
            log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    final String LEAGUE_WAR_ROOT_DIR = "./war-league";
    final String LEAGUE_WAR_SEASON_DIR = "/{season}/{clanTag}";
    final String LEAGUE_WAR_SEASON_INFO_FILE_NAME = "league-info";
    final String LEAGUE_WAR_INFO_DIR = LEAGUE_WAR_ROOT_DIR + LEAGUE_WAR_SEASON_DIR;
    final String LEAGUE_WAR_ROUND_DIR = LEAGUE_WAR_ROOT_DIR + LEAGUE_WAR_SEASON_DIR + "/round";
    final String JSON_FILE_NAME = "%s.json";

    private void writeClanWarLeagueSeasonGroup(String clanTag, ClanCurrentWarLeagueGroup clanCurrentWarLeagueGroup) {
        try {
            // directory: ./war-league/{season}/{clanTag}
            final String path = LEAGUE_WAR_INFO_DIR.replace("{season}", clanCurrentWarLeagueGroup.getSeason())
                                                   .replace("{clanTag}", clanTag);

            ClassPathResource resource = checkAndMakeDirectory(path);

            // 이하 클랜태그별 폴더
            String fileName = getLeagueWarSeasonInfoFileName();

            File file = new File(resource.getPath(), fileName);
            makeEmptyFile(file);

            writeFile(file, clanCurrentWarLeagueGroup);
        } catch (IOException e) {
            log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private String getLeagueWarSeasonInfoFileName() {
        return makeJsonFileName(LEAGUE_WAR_SEASON_INFO_FILE_NAME);
    }

    private <T> void writeFile(File file, T data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(objectMapper.writeValueAsString(data));
        writer.close();
    }

    private ClanWar findClanWarLeagueFromFile(String clanTag, String season, String warTag) {
        final String path = LEAGUE_WAR_ROUND_DIR.replace("{season}", season)
                                                .replace("{clanTag}", clanTag);

        // directory: ./war-league/{season}/{clanTag}/round/{warTag}.json
        File file = new File(path, makeJsonFileName(warTag));

        // Not Found.
        if (!file.exists()) return null;

        try {
            return objectMapper.readValue(file, ClanWar.class);
        } catch (Exception e) {
            log.error("파일({}) 파싱 오류, {}", warTag, e.getMessage(), e);
            return null;
        }
    }

    private void writeClanWarLeagueResult(String clanTag, String season, String warTag, ClanWar clanWar) {
        try {
            // directory: ./war-league/{season}/{clanTag}/round
            final String path = LEAGUE_WAR_ROUND_DIR.replace("{season}", season)
                                                    .replace("{clanTag}", clanTag);

            ClassPathResource resource = checkAndMakeDirectory(path);

            File file = new File(resource.getPath(), makeJsonFileName(warTag));
            makeEmptyFile(file);

            writeFile(file, clanWar);
        } catch (IOException e) {
            log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private void makeEmptyFile(File file) throws IOException {
        if (file.exists()) return;

        boolean result = file.createNewFile();
        if (!result) {
            throw new IOException("파일 생성 실패: " + file.getAbsolutePath());
        }
    }

    private String makeJsonFileName(String warTag) {
        return JSON_FILE_NAME.formatted(warTag);
    }

    private ClassPathResource checkAndMakeDirectory(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            File directory = new File(resource.getPath());
            directory.mkdirs();
        }
        return resource;
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
}
